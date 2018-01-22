package sslserver;

import auctioneum.network.Regulator;
import database.Database;
import general.Auction;
import general.logging.Logger;
import general.logging.TxtLogger;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
    
    public static void main(String [] args) throws Exception {
        
//        Logger.logger = new TxtLogger();
        
        new Server().start();
        
        
        
//        Database db = new Database();
//        db.insertAuction();

//        ArrayList<Auction> auctions = new ArrayList();
//        auctions.add(new Auction(1,"sad",1,1,"kjas"));
//        auctions.add(new Auction(2,"sad",2,2,"kjas"));
//        JSONArray ar = getJsonArrayAuctions(auctions);
//        System.out.println(ar);
        

    }
    
//    public static JSONArray getJsonArrayAuctions (ArrayList <Auction> auctions) {
//        JSONArray array = new JSONArray();
//        JSONObject jo = null;
//        try {
//            for (Auction a : auctions) {
//                jo = new JSONObject();
//                jo.put("a", a.getAuction_id());
//                jo.put("i", a.getAuctioneer_id());
//                jo.put("o", a.getObject_id());
//                jo.put("s", a.getAuction_status());
//                jo.put("t", a.getAuction_type());
//                array.put(jo);
//            }
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//        return array;
//    }
    
}
