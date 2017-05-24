package br.org.multimidia.multimidiavoz.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.org.multimidia.multimidiavoz.BO.ContatoBO;
import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Meta;
import br.org.multimidia.multimidiavoz.rest.SimpleRest;
import br.org.multimidia.multimidiavoz.rest.UserRest;
import br.org.multimidia.multimidiavoz.rest.response.ContatoResponse;
import br.org.multimidia.multimidiavoz.ui.adapter.AdapterLvContatos;
import br.org.multimidia.multimidiavoz.utils.Utils;


/**
 *@author Jheieme Santos da Silveira
 *
 */
public class FgtContatos extends Fragment {

    private View view = null;
    private ListView lvContatos;
    private UserRest userRest;
    private ContatoBO contatoBO;
    private Contato contato;

    /**
     * construtor da minha view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fgt_contatos, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        initComponents();
    }

    /**
     * inicializa todos meus componentes em tela
     */
    private void initComponents() {
        initR();
        initData();
        onLvContatos();
    }

    private void initData() {
        try {
            userRest = new UserRest(getContext());
            contatoBO = new ContatoBO(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onLvContatos() {
        userRest.contatos(new SimpleRest.CallbackObject() {
            @Override
            public void onSuccess(JSONObject jsonObject) throws JSONException {
                Gson gson = new Gson();
                String json = jsonObject.toString();
                ContatoResponse response = gson.fromJson(json, ContatoResponse.class);
                if (!response.getMeta().getOk()) {
                    Utils.showToast(getContext(), response.getMeta().getMessage());
                    return;
                }
                lvContatos.setAdapter(new AdapterLvContatos(getContext(), response.getContatos()));
                lvContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contato contato = (Contato) lvContatos.getAdapter().getItem(position);
                        Bundle args = new Bundle();
                        args.putSerializable("contato", contato);
                        Intent i = new Intent(getActivity(), ActAtendido.class);
                        i.putExtras(args);
                        getActivity().startActivity(i);
                    }
                });
            }
        });
    }

    private void initR() {
        lvContatos = (ListView) view.findViewById(R.id.fgt_contatos_lv);
    }
}
