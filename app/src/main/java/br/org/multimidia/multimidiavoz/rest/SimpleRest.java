package br.org.multimidia.multimidiavoz.rest;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.multimidia.multimidiavoz.domain.Contato;
import br.org.multimidia.multimidiavoz.utils.Constant;
import br.org.multimidia.multimidiavoz.utils.LocalStorage;
import br.org.multimidia.multimidiavoz.utils.Utils;
import br.org.multimidia.multimidiavoz.volley.CustomJsonArrayRequest;
import br.org.multimidia.multimidiavoz.volley.CustomJsonObjectRequest;

/**
 * @Autor Jheimes santos da silveira
 *
 */
public class SimpleRest {

	private RequestQueue rq;
	private Context context;
	private LocalStorage localStorage;

	public SimpleRest(Context context) {
		this.context = context;
		rq = Volley.newRequestQueue(context);
		localStorage = new LocalStorage(context);
	}

	/**
	 * faz uma chamada get para um server externo pela url
	 * @param url
	 * @param callback
	 */
	public void getCallObjectRequest(String url, final CallbackObject callback) {

		CustomJsonObjectRequest request = new CustomJsonObjectRequest(
			url,
			null,
			new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					try {
						callback.onSuccess(response);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			},
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
				}
		});
		
		request.setTag("tag");
		rq.add(request);
	}

	/**
	 * faz uma chamada post para um server
	 * @param url
	 * @param params
	 * @param callback
	 */
	public void postCallObjectRequest(String url, Map<String, String> params, final CallbackObject callback) throws JSONException {

		CustomJsonObjectRequest request = new CustomJsonObjectRequest(
				Request.Method.POST,
				url,
				params,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							callback.onSuccess(response);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
					}
				});

		Contato contato = (Contato) localStorage.getItem(Constant.USER_TOKEN, Contato.class);
		if (contato != null) {
			String token = (String) localStorage.getItem(contato.getNumero(), String.class);
			Map<String, String> header = new HashMap<>();
			header.put("Authorization", "Bearer " + token);
			request.setHeaders(header);
		}
		rq.add(request);
	}

	public void postCallArrayRequest(String url, Map<String, Object> params, final CallbackArray callback)  throws JSONException {
		Map<String, String> newParams = Utils.convertToMap(params);

		CustomJsonArrayRequest request = new CustomJsonArrayRequest(
				Request.Method.POST,
				url,
				newParams,
				new Response.Listener<JSONArray>(){
					@Override
					public void onResponse(JSONArray response) {
						callback.onSuccess(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
					}
				});

		request.setTag("tag");
		rq.add(request);
	}

	public void getCallArrayRequest(String url, final CallbackArray callback) {

		CustomJsonArrayRequest request = new CustomJsonArrayRequest(
				Request.Method.GET,
				url,
				null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						List<Map<String,Object>> retMap = new ArrayList<>();
						callback.onSuccess(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG);
					}
				});

		request.setTag("tag");
		rq.add(request);
	}

	public interface CallbackObject {
		void onSuccess(JSONObject jsonObject) throws JSONException;
	}
	public interface CallbackArray {
		void onSuccess(JSONArray jsonArray);
	}
}
