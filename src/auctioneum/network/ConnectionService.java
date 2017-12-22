package auctioneum.network;


import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionService implements Runnable{

    private static final int MIN_NODES_NUM = 5;

    private Node owner;

    private Socket connection;

    private Request request;

    public ConnectionService(Node owner, Socket connection){
        this.owner = owner;
        this.connection = connection;
    }



    @Override
    public void run() {
        try {
            Regulator self = (Regulator) this.owner;
            if (!self.getAdvertisedPeers().isEmpty()){
                ObjectOutputStream oos = new ObjectOutputStream(this.connection.getOutputStream());
                int nodesKept = self.getAdvertisedPeers().size();
                int peersToSend = nodesKept > MIN_NODES_NUM ? (int)(0.1*nodesKept) : nodesKept;
                oos.writeObject(peersToSend);
                oos.flush();
                for (int i=0; i<peersToSend; i++){
                    Node next = (Node) self.getAdvertisedPeers().keySet().toArray()[i];
                    int timesSent = self.getAdvertisedPeers().get(next)+1;
                    self.getAdvertisedPeers().put(next,timesSent);
                    oos.writeObject(next);
                    oos.flush();
                }
                oos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
