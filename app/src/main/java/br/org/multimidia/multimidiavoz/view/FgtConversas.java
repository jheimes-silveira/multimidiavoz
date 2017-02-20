package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.org.multimidia.multimidiavoz.R;


/**
 * @author Jheieme Santos da Silveira
 */
public class FgtConversas extends Fragment {
    private View view = null;

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
    }


    /**
     * inicializa todos meus componentes em tela
     */
    private void initComponents() {
        initR();
        super.onStart();
    }

    private void initR() {
    }
}
