package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Conversa;
import br.org.multimidia.multimidiavoz.ui.adapter.AdapterLvConversas;


/**
 * @author Jheieme Santos da Silveira
 */
public class FgtConversas extends Fragment {
    private View view = null;
    private ListView lvConversas;

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
    }


    /**
     * inicializa todos meus componentes em tela
     */
    private void initComponents() {
        initR();
        super.onStart();
    }

    private void onLvConversas() {
        List<Conversa> conversas = new ArrayList<>();
        for(int i = 0 ; i < 9 ; i++) {
            Conversa conversa = new Conversa();
            Calendar calendar = Calendar.getInstance();
            conversa.setDtaInicio(calendar.getTime());
            calendar.add(Calendar.MINUTE, 5);
            conversa.setDtaFinal(calendar.getTime());
            conversa.setNumeroDestino("(34) 9 9226434"+ (i+1));
            conversa.setUsuarioDestino("Funalo de tal");
            conversas.add(conversa);
        }
        lvConversas.setAdapter(new AdapterLvConversas(getContext(), conversas));
    }

    private void initR() {
        lvConversas = (ListView) view.findViewById(R.id.fgt_conversas_lv);
    }
}
