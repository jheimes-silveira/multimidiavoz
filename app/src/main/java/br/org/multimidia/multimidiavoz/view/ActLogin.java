package br.org.multimidia.multimidiavoz.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import br.org.multimidia.multimidiavoz.BO.ContatoBO;
import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.domain.Meta;
import br.org.multimidia.multimidiavoz.enuns.Action;
import br.org.multimidia.multimidiavoz.rest.SimpleRest;
import br.org.multimidia.multimidiavoz.rest.UserRest;
import br.org.multimidia.multimidiavoz.utils.Constant;
import br.org.multimidia.multimidiavoz.utils.LocalStorage;
import br.org.multimidia.multimidiavoz.utils.MobileApp;
import br.org.multimidia.multimidiavoz.utils.Router;
import br.org.multimidia.multimidiavoz.utils.Utils;

public class ActLogin extends AppCompatActivity {

    private EditText telefone;
    private EditText senha;
    private UserRest userRest;
    private ContatoBO bo;
    private Context context;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new MobileApp().getApplication().getContatoLogado(ActLogin.this) != null) {
            Router.onCreateActivity(ActLogin.this, ActPrincipal.class);
            finish();
        }
        setContentView(R.layout.activity_act_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponent();
    }

    private void initComponent() {
        initData();
        initView();
    }
    private void initData() {
        context = ActLogin.this;
        try {
            bo = new ContatoBO(context);
            userRest = new UserRest(context);
            localStorage = new LocalStorage(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onCadastrar(View view) {
        startActivity(new Intent(context, ActCadastro.class));
    }

    private void initView() {
        telefone = (EditText) findViewById(R.id.et_telefone);
        senha = (EditText) findViewById(R.id.et_senha);
    }

    public void onLogin(View view) {
        if (verificarContato()) {
                userRest.logar(
                        telefone.getText().toString(),
                        senha.getText().toString(),
                        new SimpleRest.CallbackObject() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) throws JSONException {
                                Gson gson = new Gson();
                                Meta meta = gson.fromJson(jsonObject.getJSONObject("meta").toString(), Meta.class);
                                Contato contato = gson.fromJson(jsonObject.getJSONObject("user").toString(), Contato.class);
                                String token = (String) jsonObject.get("token");
                                bo.create(contato);
                                localStorage.setItem(Constant.USER_TOKEN, contato);
                                localStorage.setItem(contato.getNumero(), token);
                                contato = new MobileApp().getApplication().getContatoLogado(context);
                                if (contato != null) {
                                    Intent intent = new Intent(context, ActPrincipal.class);
                                    intent.putExtra(Action.MESSAGE.toString(), meta.getMessage());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
    }

    private boolean verificarContato() {
        boolean flag = true;
        if(telefone.getText().length() <= 0){
            telefone.setError("Campo obrigatório");
            flag = false;
        }

        if(senha.getText().length() <= 0){
            senha.setError("Campo obrigatório");
            flag = false;
        }

        return flag;
    }
}
