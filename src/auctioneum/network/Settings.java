package auctioneum.network;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


/**
 * Configuration Settings of Nodes.
 */
public class Settings {

    public static InetAddress IP;

    public static int TRANSACTIONS_PORT;

    public static int VALIDATIONS_PORT;

    public static List<Node> PEERS;

    static {
        try {
            IP = InetAddress.getLocalHost();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        TRANSACTIONS_PORT = 4572;
        VALIDATIONS_PORT = 8338;
    }

}
