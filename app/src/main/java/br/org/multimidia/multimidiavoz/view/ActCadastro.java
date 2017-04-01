package br.org.multimidia.multimidiavoz.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.rest.SimpleRest;
import br.org.multimidia.multimidiavoz.rest.UserRest;

public class ActCadastro extends AppCompatActivity {

    private UserRest userRest;
    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextSenha;

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
        initView();
        onSalvarUsuario();
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
                            Log.i("Log", jsonObject.toString());
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
