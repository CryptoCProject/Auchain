/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author vagelis
 */
public class Json {
    
    public static JSONObject getJsonKeys(String puk, String prk) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("u", puk);
            jo.put("r", prk);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jo;
    }
    
}
