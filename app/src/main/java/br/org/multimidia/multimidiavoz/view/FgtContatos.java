package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.ui.adapter.AdapterLvContatos;


/**
 *@author Jheieme Santos da Silveira
 *
 */
public class FgtContatos extends Fragment {

    private View view = null;
    private ListView lvContatos;
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
        onLvContatos();
    }

    private void onLvContatos() {
        List<Contato> profiles = new ArrayList<>();
        for(int i = 0 ; i < 9 ; i++) {
            Contato user = new Contato();
            user.setNome("Nome User"+ (i+1));
            user.setNumero("(34) 9 9226434"+ (i+1));
            profiles.add(user);
        }
        lvContatos.setAdapter(new AdapterLvContatos(getContext(), profiles));
    }

    private void initR() {
        lvContatos = (ListView) view.findViewById(R.id.fgt_contatos_lv);
    }
}
