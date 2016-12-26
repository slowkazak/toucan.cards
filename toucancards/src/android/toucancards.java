package ru.slowkazak.toucancards;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.Iterator;
import android.net.Uri;
import java.lang.reflect.Field;
import android.content.ActivityNotFoundException;
import android.util.Log;
import android.os.Bundle;


/**
 * This class echoes a string called from JavaScript.
 */
public class toucancards extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("payment")) {
            JSONObject obj = args.getJSONObject(0);
            this.payment(obj, callbackContext);
            return true;
        }
        return false;
    }

  /**
     * toucanpayment
     */
    private void payment(JSONObject obj, CallbackContext callbackContext) {
       try {
        if (obj != null) {
Context context=this.cordova.getActivity().getApplicationContext();
          Intent intent = new Intent("ru.toucan.PAYMENT");




/*
                    Iterator<String> iter = obj.keys();

                    					while (iter.hasNext()) {
                    						key = iter.next();
                    						value = obj.getString(key);
                    						 intent.putExtra(key, value);
                    					}
*/
     intent.putExtra("PackageName", "com.readyscript.dk.storemanagement");
            intent.putExtra("SecureCode", "1234");

            intent.putExtra("Amount", 100);
            intent.putExtra("Description", "23234234");
             cordova.getActivity().startActivityForResult(intent, 4);
                    callbackContext.success(obj.getString("Amount"));
                } else {
                    callbackContext.error("Expected one non-empty string argument.");
                }
          } catch(Exception e) {

          }


    }


        /**
         * Обработка резулта
         *
         * @param requestCode
         * @param resultCode
         * @param data
         */
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

        }

}
