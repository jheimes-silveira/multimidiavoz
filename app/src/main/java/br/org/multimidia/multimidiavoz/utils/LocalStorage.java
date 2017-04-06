package br.org.multimidia.multimidiavoz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jheimes on 24/02/17.
 */

public class LocalStorage<T> {


    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    public LocalStorage(Context context) {

        sharedpreferences = context.getSharedPreferences(null, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    /**
     * construtor para novo storagem de arquivos
     * @param context
     * @param key
     */
    public LocalStorage(Context context, String key) {

        sharedpreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void setList(String key, List<T> list) {

        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void setItem(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        set(key, json);
    }

    public Object getItem(String key, Class clazz) {
        return get(key, clazz);
    }

    public List<T> getList(String key) {
        TypeToken<T> typeToken = (TypeToken) new TypeToken<T>() {}.getType();
        String stringJson = sharedpreferences.getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(stringJson, (Type) typeToken);
    }

    private Object get(String key, Class clazz) {
        String stringJson = sharedpreferences.getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(stringJson, clazz);
    }

    private void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
}
