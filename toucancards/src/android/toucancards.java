package ru.slowkazak.toucancards;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.util.Log;
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
            this.callbackctx = callbackContext;
            this.payment(obj);
            return true;
        }
        return false;
    }

    CallbackContext callbackctx;

    /**
     * toucanpayment
     */
    private void payment(JSONObject obj) {
        try {
            if (obj != null) {
                Context context = cordova.getActivity();
                Intent intent = new Intent("ru.toucan.PAYMENT");

                intent.putExtra("PackageName", "com.readyscript.dk.storemanagement");
                intent.putExtra("SecureCode", obj.getString("SecureCode"));

                intent.putExtra("Amount", obj.getInt("Amount"));
                intent.putExtra("Description", obj.getString("Description"));

                /**
                 *
                 * Добавить обработку json. Объект obj должен перебираться по циклу
                 * внутри цикла должна выполняться функция intent.putExtra(key,value) где key - ключ из json объекта, value - значение этого ключа
                 *
                 * Например при переборе обэекта {SecureCode: "1234"}
                 * в интент должен добавиться параметр intent.putExtra("SecureCode", "1234")
                 * Все свойста должны быть добавлены "как есть", например {SecureCode: "1234" , Amount: 12345} будет преобразован в intent.putExtra("SecureCode", "1234")
                 * и intent.putExtra("Amount", 12345) то есть поля string должны быть помещены как string, number как number и тд.
                 *
                 */

                cordova.setActivityResultCallback(this);
                cordova.getActivity().startActivityForResult(intent, 4);
                //  callbackContext.success(obj.getString("Amount"));
            } else {
                // callbackContext.error("Expected one non-empty string argument.");
            }
        } catch (Exception e) {

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
        JSONObject res = new JSONObject();
        try {
            JSONObject json = new JSONObject();

            for (String key : data.getExtras().keySet()) {
                Object value = data.getExtras().get(key);
                if (value != null) {
                    json.put(key, value);
                }
            }

            if (requestCode == 4) {
                res.put("action", "ru.toucan.PAYMENT");
                if (resultCode == -1) {


                    res.put("success", new Boolean(true));

                } else if (resultCode == 0) {
                    res.put("success", new Boolean(false));
                }
                res.put("additional_data", json);
            }
            this.callbackctx.success(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}