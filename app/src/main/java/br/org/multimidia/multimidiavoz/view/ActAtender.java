package br.org.multimidia.multimidiavoz.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.FractionRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.github.clans.fab.FloatingActionButton;

import br.org.multimidia.multimidiavoz.ParamsBundle;
import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.utils.LocalStorage;
import br.org.multimidia.multimidiavoz.utils.Utils;

public class ActAtender extends Fragment {

    private View view;
    private TextView tvNome;
    private FloatingActionButton btnAtender;
    private FloatingActionButton btnDesligar;
    private String nome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.activity_act_atender, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponentes();
        onBtnAtender();
        onBtnDesligar();
    }

    private void onBtnDesligar() {
        btnDesligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActPrincipal act = (ActPrincipal) getActivity();
                act.onEncerrarChamada();
            }
        });
    }

    private void onBtnAtender() {
        btnAtender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActPrincipal act = (ActPrincipal) getActivity();
                act.onAtenderChamada(nome);
            }
        });
    }

    private void initComponentes() {
        initViews();
        initDados();
        initTv();
    }

    private void initTv() {
        tvNome.setText(nome);
    }


    private void initViews() {
        tvNome = (TextView) view.findViewById(R.id.tvNome);
        btnAtender = (FloatingActionButton) view.findViewById(R.id.btnAtender);
        btnDesligar = (FloatingActionButton) view.findViewById(R.id.btnDesligar);

    }

    private void initDados() {
        nome = getArguments().getString("nome");
    }
}
