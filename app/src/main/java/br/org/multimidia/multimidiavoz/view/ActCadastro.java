package br.org.multimidia.multimidiavoz.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import net.majorkernelpanic.streaming.SessionBuilder;

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
import br.org.multimidia.multimidiavoz.utils.Router;
import br.org.multimidia.multimidiavoz.utils.Utils;

public class ActCadastro extends AppCompatActivity {

    private UserRest userRest;
    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextSenha;
    private ContatoBO bo;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();
    }

    private void initComponents(){
        initData();
        initView();
        onSalvarUsuario();
    }

    private void initData() {
        context = ActCadastro.this;
        try {
            bo = new ContatoBO(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onSalvarUsuario() {
        if(verificarDados()){
            userRest.createUser(
                    editTextNome.getText().toString(),
                    editTextSenha.getText().toString(),
                    editTextTelefone.getText().toString(),
                    new SimpleRest.CallbackObject() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) throws JSONException {
                            Gson gson = new Gson();
                            Meta meta = gson.fromJson(jsonObject.getJSONObject("meta").toString(), Meta.class);
                            Utils.showToast(ActCadastro.this, meta.getMessage());
                        }
                    });
        }
    }

    private boolean verificarDados() {

        if(editTextNome.getText().length() <= 0){
            editTextNome.setError("Campo obrigatório");
            return false;
        }

        if(editTextTelefone.getText().length() <= 0){
            editTextTelefone.setError("Campo obrigatório");
            return false;
        }

        if(editTextSenha.getText().length() <= 0){
            editTextSenha.setError("Campo obrigatório");
            return false;
        }

        return true;
    }

    public void onCadastrar(View view){
        onSalvarUsuario();
    }

    private void initView() {
        userRest = new UserRest(ActCadastro.this);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);
    }
}
