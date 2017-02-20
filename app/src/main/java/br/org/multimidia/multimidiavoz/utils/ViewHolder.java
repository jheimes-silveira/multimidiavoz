package br.org.multimidia.multimidiavoz.utils;

import android.view.View;

import java.util.HashMap;

public class ViewHolder {

	public static final String KEY = "keyViewHolder";

	private HashMap<String, View> propertys;

	private HashMap<String, Object> propertysObjects;

	public ViewHolder() {
		propertys = new HashMap<String, View>();
		propertysObjects = new HashMap<String, Object>();
	}

	public void setProperty(View view, String propertyName) {
		propertys.put(propertyName, view);
	}

	public View getProperty(String propertyName) {
		return propertys.get(propertyName);
	}
	
	public void setPropertyObject(Object object, String propertyName) {
		propertysObjects.put(propertyName, object);
	}
	
	public Object getPropertyObject(String propertyName) {
		return propertysObjects.get(propertyName);
	}





}
