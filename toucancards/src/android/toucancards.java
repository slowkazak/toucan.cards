package ru.slowkazak.toucancards;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class cards extends CordovaPlugin {

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
                    callbackContext.success(obj.toString());
                } else {
                    callbackContext.error("Expected one non-empty string argument.");
                }
          } catch(Exception e) {
               callbackContext.error(e);
          }
    }
}
