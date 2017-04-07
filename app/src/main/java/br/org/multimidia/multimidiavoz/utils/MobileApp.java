package br.org.multimidia.multimidiavoz.utils;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import br.org.multimidia.multimidiavoz.domain.Contato;

public class MobileApp extends Application {

	public static final String VERSION = "1.0";

	private static MobileApp instance;

	private Contato contatoLogado;

	private String token;

	public MobileApp() {
		instance = this;
	}

	public static MobileApp getApplication() {
		return instance;
	}

	public Contato getContatoLogado(Context context) {
		if (contatoLogado != null) {
			return contatoLogado;
		}
		LocalStorage localStorage = new LocalStorage(context);
		contatoLogado = (Contato) localStorage.getItem(Constant.USER_TOKEN, Contato.class);
		if (contatoLogado != null) {
			token = (String) localStorage.getItem(contatoLogado.getNumero(), String.class);
		}
		return contatoLogado;
	}

	public String getToken(Context context) {
		if (token != null && token != "") {
			return token;
		}
		LocalStorage localStorage = new LocalStorage(context);
		contatoLogado = (Contato) localStorage.getItem(Constant.USER_TOKEN, Contato.class);
		if (contatoLogado != null) {
			token = (String) localStorage.getItem(contatoLogado.getNumero(), String.class);
		}
		return token;
	}

	public void logout(Context context) {
		LocalStorage localStorage = new LocalStorage(context);
		localStorage.setItem(Constant.USER_TOKEN, null);
		localStorage.setItem(contatoLogado.getNumero(), null);
		this.contatoLogado = null;
		this.token = null;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public void onCreate() {
		System.out.println(">>>>>>>>>>>>>>> CRIANDO APLICACAO <<<<<<<<<<<<<<<<<<");
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		System.out.println("ON TERMINATE");
	}
}
