package br.org.multimidia.multimidiavoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActMain extends AppCompatActivity {

    private Button btnAtender;
    private Button btnAtendido;
    private Button btnCadastro;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        onAtender();
    }

    private void onAtender() {
        btnAtender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initViews() {
        btnAtender = (Button) findViewById(R.id.atender);
        btnAtendido = (Button) findViewById(R.id.atendido);
        btnCadastro = (Button) findViewById(R.id.cadastro);
        btnlogin = (Button) findViewById(R.id.login);
    }
}
