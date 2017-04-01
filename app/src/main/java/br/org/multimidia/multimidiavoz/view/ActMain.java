package br.org.multimidia.multimidiavoz.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.utils.Router;

public class ActMain extends AppCompatActivity {

    private Button btnAtender;
    private Button btnAtendido;
    private Button btnCadastro;
    private Button btnlogin;
    private Button btnPrincipal;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        context = ActMain.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
        onAtender();
        onAtendido();
        onCadastro();
        onPrincipal();
        onLogin();
    }

    private void onPrincipal() {
        btnPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.onCreateActivity(context, ActPrincipal.class);
            }
        });
    }

    private void onLogin() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.onCreateActivity(context, ActLogin.class);
            }
        });
    }

    private void onCadastro() {
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.onCreateActivity(context, ActCadastro.class);
            }
        });
    }

    private void onAtendido() {
        btnAtendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.onCreateActivity(context, ActAtendido.class);
            }
        });
    }

    private void onAtender() {
        btnAtender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.onCreateActivity(context, ActAtender.class);
            }
        });
    }

    private void initViews() {
        btnAtender = (Button) findViewById(R.id.atender);
        btnAtendido = (Button) findViewById(R.id.atendido);
        btnCadastro = (Button) findViewById(R.id.cadastro);
        btnlogin = (Button) findViewById(R.id.login);
        btnPrincipal = (Button) findViewById(R.id.principal);
    }
}
