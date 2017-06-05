package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.utils.Utils;

public class ActAtendido extends Fragment {

    private TextView tvNome;
    private FloatingActionButton btnDesligar;
    private View view;
    private String nome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.activity_act_atendido, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponentes();
    }

    private void initComponentes() {
        nome = getArguments().getString("nome");
        initViews();
        tvNome.setText(nome);
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


    private void initViews() {
        tvNome = (TextView) view.findViewById(R.id.tvNome);
        btnDesligar = (FloatingActionButton) view.findViewById(R.id.btnDesligar);
    }

}
