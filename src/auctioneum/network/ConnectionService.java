package auctioneum.network;


import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionService implements Runnable{

    private static final int NODES_THRES_DOWN = 5;

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
                int peersToSend = nodesKept > NODES_THRES_DOWN? (int)(0.1*nodesKept) : nodesKept;
                oos.writeObject(peersToSend);
                oos.flush();
                for (int i=0; i<peersToSend; i++){
                    oos.writeObject(self.getAdvertisedPeers().values().toArray()[i]);
                    oos.flush();
                }
                oos.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
