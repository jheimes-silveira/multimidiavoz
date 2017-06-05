package br.org.multimidia.multimidiavoz.rest;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import br.org.multimidia.multimidiavoz.domain.Meta;
import br.org.multimidia.multimidiavoz.utils.Constant;
import br.org.multimidia.multimidiavoz.utils.MobileApp;
import br.org.multimidia.multimidiavoz.utils.Utils;

/**
 * Created by jheimesilveira on 11/03/2016.
 */
public class UserRest extends SimpleRest {

    private Context context;

    public UserRest(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * lista todos os contatos cadastrados
     * @param callback retorno da chamada
     */
    public void contatos(final CallbackObject callback) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("idUser", MobileApp.getApplication().getContatoLogado(context).getId().toString());
            String url = Constant.URL + "/buscar-todos-contatos";
            postCallObjectRequest(url, map, new CallbackObject() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    try {
                        callback.onSuccess(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * cria um novo usuario no sistema
     * @param callback retorno da chamada
     */
    public void createUser(String login,
                           String password,
                           String phone,
                           final CallbackObject callback) {
        Map<String, String> map = new HashMap<>();
        map.put("nome", login);
        map.put("senha", password);
        map.put("telefone", phone);
        try {
            String url = Constant.URL + "/login/register/usuario";
            postCallObjectRequest(url, map, new CallbackObject() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    try {
                        callback.onSuccess(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * logar no sistema
     * @param callback retorno da chamada
     */
    public void logar(String login,
                           String password,
                           final CallbackObject callback) {
        Map<String, String> map = new HashMap<>();
        map.put("telefone", login);
        map.put("senha", password);
        try {
            String url = Constant.URL + "/login/logar";
            postCallObjectRequest(url, map, new CallbackObject() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    try {
                        callback.onSuccess(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Utils.showToast(context, e.getMessage());
                    }
                }
            });
        } catch (JSONException e) {
            Utils.showToast(context, e.getMessage());
            e.printStackTrace();
        }
    }

}
