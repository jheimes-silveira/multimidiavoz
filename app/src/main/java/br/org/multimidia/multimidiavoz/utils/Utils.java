package br.org.multimidia.multimidiavoz.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.org.multimidia.multimidiavoz.R;

public class Utils<T>{
  
	public static int tamMax(String mask) {
        String novaString = removeCaracteresEspeciais(mask);
        return novaString.length();  
    }

	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

    public static String removeCaracteresEspeciais(String str) {
  
        String[] caracterSpecial = {"\\.", ",", "-", ":", "\\(", "\\)", "�", "\\|", "\\\\", "�", "\\/"};
  
        for (int i = 0; i < caracterSpecial.length; i++) {
            str = str.replaceAll(caracterSpecial[i], "");
        }  
  
        return str;  
    }

	/**
	 * extrair valor expecifico de um campo
	 * @param value campo
	 * @param entities array
     * @return array com o campo desejado
     */
	public List<T> extractValue(String value, List<T> entities) {
		List<T> list = new ArrayList<>();
		for (int i = 0 ; i < entities.size() ; i++) {
			T entity = entities.get(i);
			Field[] fields = entity.getClass().getDeclaredFields();
			for (Field fild : fields) {
				if (fild.getName().equals(value)) {
					try {
						fild.setAccessible(true);
						list.add((T) fild.get(entity));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return list;
	}

	public static void showToast(Context context, String message){
		Toast.makeText(context,
				message,
				Toast.LENGTH_LONG)
				.show();
	}
	public static void showToast(Context context, String message, int duration){
		Toast.makeText(context,
				message,
				Toast.LENGTH_LONG)
				.show();
	}

	public static int convertDateToSeconds(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int s = c.get(Calendar.MINUTE);
		s = s * 60;
		s += c.get(Calendar.SECOND);
		return s;
	}
	/**
	 * converter file para bitmap
	 * @param file arquivo
	 * @return bitmap
	 */
	public static Bitmap convertFileToBitmap(File file) {
		Bitmap bitmap = null;
		if(file != null) {
			bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			bitmap = Utils.getRoundedShape(bitmap, 150);
		}
		return bitmap;
	}

	public static String formatDate(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static String formatDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * substring com tres pontos no final
	 * @param txt
	 * @param maxCaracter
	 * @return
	 */
	public static String substring(String txt, int maxCaracter) {
		if(txt.length() > maxCaracter) {
			return txt.substring(0,maxCaracter) + "...";
		} else {
			return txt;
		}
	}
	public static String formatFloat(Float value, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(value);
	}

    /**
     * Cria um array de String com os valores passados. Caso um valor seja nulo, será passado uma String vazia.
     * @param objs
     * @return
     */
    public static String[] createArray(Object... objs) {
    	String[] resultado = new String[objs.length];
    	for(int i = 0; i < objs.length; i++) {
    		Object o = objs[i];
    		resultado[i] = (o == null ? "" : o.toString());
    	}
    	return resultado;
    }
    
	public static void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
		
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists() && !targetLocation.mkdirs()) {
				throw new IOException("Cannot create dir " + targetLocation.getAbsolutePath());
			}
			
			String[] children = sourceLocation.list();
			for (int i=0; i<children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]),
						new File(targetLocation, children[i]));
			}
		} else {
			
			if (targetLocation.exists()) {
				targetLocation.delete();
			}
			
			// make sure the directory we plan to store the recording in exists
			File directory = targetLocation.getParentFile();
			if (directory != null && !directory.exists() && !directory.mkdirs()) {
				throw new IOException("Cannot create dir " + directory.getAbsolutePath());
			}
			
			
			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);
			
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
	
	public static boolean verificarConexao(Context context) {
	    boolean conectado;
	    
		ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (conectivtyManager.getActiveNetworkInfo() != null
	            && conectivtyManager.getActiveNetworkInfo().isAvailable()
	            && conectivtyManager.getActiveNetworkInfo().isConnected()) {
	    	conectado = true;
	    } else {
	        conectado = false;
	    }
	    return conectado;
	}
	
    public static int dpToPx(Context ctx, int dp) {
    	final float scale = ctx.getResources().getDisplayMetrics().density;
    	return (int) (dp * scale + 0.5f);

    }

    public static Bitmap getRoundedShape(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                finalBitmap.getHeight() / 2 + 0.7f,
                finalBitmap.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;

    }
	/**
	 * converter Map<K,V> para JSONObject
	 * @param params
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject convertToJson(Map<String, Object> params) throws JSONException {
		JSONObject json = new JSONObject();

		for (Map.Entry<String, Object> map : params.entrySet()) {
			json.put(map.getKey(), map.getValue());
		}
		return json;
	}

	public static Map<String, Object> convertToMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while(keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if(value instanceof JSONArray) {
				value = convertToList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = convertToMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Map<String, Object>> convertToMap(JSONArray array) throws JSONException {

		List<Map<String, Object>> map = new ArrayList<>();

		for (int i = 0 ; i < array.length() ; i++) {
			JSONObject object = array.getJSONObject(i);
			Map<String, Object> itemMap = new HashMap<>();
			Iterator<String> keysItr = object.keys();
			while(keysItr.hasNext()) {
				String key = keysItr.next();
				Object value = object.get(key);

				if(value instanceof JSONArray) {
					value = convertToList((JSONArray) value);
				}

				else if(value instanceof JSONObject) {
					value = convertToMap((JSONObject) value);
				}
				itemMap.put(key, value);
			}
			map.add(itemMap);
		}

		return map;
	}

	public static List<Object> convertToList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<>();
		for(int i = 0; i < array.length(); i++) {
			Object value = array.get(i);

			if(value instanceof JSONArray) {
				value = convertToList((JSONArray) value);
			}

			else if(value instanceof JSONObject) {
				value = convertToMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	/**
	 * converte um List<E> para um array String[]
	 * @param list params de entrada
	 * @return retorma um array
	 */
	public static String[] convertToArray(List<String> list) {
		String[] array = new String[list.size()];
		for (int i = 0 ; i < list.size() ; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	public static void setFragmentReplacePage(FragmentManager supportFragmentManager, Fragment fgt) {
		FragmentManager fm = supportFragmentManager;
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.containerActPrincipal, fgt).commit();
	}

	public static void setFragmentReplacePage(FragmentManager supportFragmentManager, Fragment fgt, int referenceFrameLayout) {
		FragmentManager fm = supportFragmentManager;
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(referenceFrameLayout, fgt).commit();
	}
	/**
	 * converter para <>String, String</>
	 * @param map
	 * @return
	 */
	public static Map<String, String> convertToMap(Map<String,Object> map) {
		Map<String,String> newMap =new HashMap<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				newMap.put(entry.getKey(), (String) entry.getValue());
			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
		}
		return newMap;
	}
}