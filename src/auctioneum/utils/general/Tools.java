package auctioneum.utils.general;

import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class Tools {
    
    public static String SUCCESS = "1";
    public static String NOT_SUCCESS = "2";
    public static String NAME_EXIST = "3";
    public static String WRONG_CREDENTIALS = "4";
    public static String WRONG_OTP = "5";
    public static String OTP_ERROR = "6";
    public static String PRIVATE_KEY_ACK = "7";

    public static String SIGN_UP = " a";
    public static String SIGN_UP_CONFIRM = " b";

    public static String LOG_IN = " c";
    public static String LOG_IN_CONFIRM = " d";

    public static String OTP = " e";
    public static String OTP_CONFIRM = " f";

    public static String MAIN = " g";
    public static String MAIN_CONFIRM = " h";
    
    public static String PRIVATE_KEY = " i";
    
    /*
    if you want to send something (for example):
    client.sendMessage(T.SIGN_UP + T.getJson(new String[]{"n", name, "p", pwd, "e", email}).toString());
    */
    public static JSONObject getJson (String ... strings) {
        JSONObject jo = new JSONObject();
        HashMap <String, String> map = new HashMap<>();
        int i = 0;
        try {
            for(;;) {
                try {
                    map.put(strings[i], strings[i + 1]);
                    i += 2;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    break;
                }
            }
            for (String k : map.keySet()) {
                jo.put(k, map.get(k));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jo;
    }
    
}

