package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.org.multimidia.multimidiavoz.BO.ContatoBO;
import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
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
        onContato();
        onConversas();
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
                lvContatos.setAdapter(new AdapterLvContatos(getActivity(), response.getContatos()));
                lvContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Contato contato = (Contato) lvContatos.getAdapter().getItem(position);
                        ActPrincipal act = (ActPrincipal) getActivity();
                        act.initCall(contato);
                    }
                });
            }
        });
    }

    private void onContato() {
        Button btn = (Button) view.findViewById(R.id.btnContatos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setFragmentReplacePage(getActivity().getSupportFragmentManager(),
                        new FgtContatos());
            }
        });
    }
    private void onConversas() {
        Button btn = (Button) view.findViewById(R.id.btnConversar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setFragmentReplacePage(getActivity().getSupportFragmentManager(),
                        new FgtConversas());
            }
        });
    }
    private void initR() {
        lvContatos = (ListView) view.findViewById(R.id.fgt_contatos_lv);
    }
}
