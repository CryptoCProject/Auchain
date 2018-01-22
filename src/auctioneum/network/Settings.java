package auctioneum.network;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Configuration Settings of Nodes.
 */
public class Settings {

    public static InetAddress IP;

    public static int TRANSACTIONS_PORT;

    public static int VALIDATIONS_PORT;

    public static List<InetAddress> PEERS;

    public static Regulator REGULATOR;


    static {
        try {
            IP = InetAddress.getLocalHost();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        TRANSACTIONS_PORT = 4572;
        VALIDATIONS_PORT = 8338;
        PEERS = new ArrayList<>();
        REGULATOR = new Regulator();
        REGULATOR.setPort(7777);
        try {
            REGULATOR.setIp(InetAddress.getByName("zafeiratosv.ddns.net"));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
