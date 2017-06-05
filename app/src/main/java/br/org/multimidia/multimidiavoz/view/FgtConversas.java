package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.org.multimidia.multimidiavoz.BO.ConversaBO;
import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Conversa;
import br.org.multimidia.multimidiavoz.ui.adapter.AdapterLvConversas;
import br.org.multimidia.multimidiavoz.utils.Utils;


/**
 * @author Jheieme Santos da Silveira
 */
public class FgtConversas extends Fragment {
    private View view = null;
    private ListView lvConversas;
    private ConversaBO conversaBO;

    /**
     * construtor da minha view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fgt_conversas, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponents();
        onLvConversas();
        onContato();
        onConversas();
    }


    /**
     * inicializa todos meus componentes em tela
     */
    private void initComponents() {
        initR();
        initDados();
    }

    private void initDados() {
        try {
            conversaBO = new ConversaBO(getContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onLvConversas() {
        List<Conversa> conversas = conversaBO.findAll();
        lvConversas.setAdapter(new AdapterLvConversas(getContext(), conversas));
    }

    private void initR() {
        lvConversas = (ListView) view.findViewById(R.id.fgt_conversas_lv);
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
}
