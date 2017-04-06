package br.org.multimidia.multimidiavoz.rest;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.org.multimidia.multimidiavoz.utils.Constant;

/**
 * Created by jheimesilveira on 11/03/2016.
 */
public class UserRest extends SimpleRest {

    public UserRest(Context context) {
        super(context);
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
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}