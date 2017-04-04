package br.org.multimidia.multimidiavoz.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jheimes on 19/02/17.
 */

public class Navigator {

    private static String keyFgtActivity = null;

    public static void onCreateActivityFinish(Activity actActual, Class activityClass) {
        Intent intent = new Intent(actActual, activityClass);
        actActual.startActivity(intent);
        actActual.finish();
    }

    public static void onCreateActivity(Context context, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void onCreateActivity(Context context, Class activityClass, Bundle args) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtras(args);
        context.startActivity(intent);
    }

    public static void onCreateActivityForResult(Activity actActual, Class activityClass, int requestCode) {
        Intent intent = new Intent(actActual, activityClass);
        actActual.startActivityForResult(intent, requestCode);
    }

    public static void onCreateActivityForResult(Activity actActual, Class activityClass, int requestCode, Bundle args) {
        Intent intent = new Intent(actActual, activityClass);
        intent.putExtras(args);
        actActual.startActivityForResult(intent, requestCode);
    }

    public static void setFragmentReplacePage(FragmentManager supportFragmentManager, Fragment fgt, int referenceFrameLayout) {
        keyFgtActivity = fgt.getClass().getName();
        FragmentManager fm = supportFragmentManager;
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(referenceFrameLayout, fgt).commit();
    }

    public String getKeyFgtActivity() {
        return keyFgtActivity;
    }

    public void setKeyFgtActivity(String keyFgtActivity) {
        this.keyFgtActivity = keyFgtActivity;
    }

}
