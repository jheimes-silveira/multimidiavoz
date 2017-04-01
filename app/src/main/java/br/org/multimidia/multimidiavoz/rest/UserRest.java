package br.org.multimidia.multimidiavoz.rest;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
            String url = "http://172.20.10.7:8080/register/usuario";
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
