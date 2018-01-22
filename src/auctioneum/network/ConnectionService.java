package auctioneum.network;


import auctioneum.blockchain.Account;
import auctioneum.utils.keys.RSA;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;

public class ConnectionService implements Runnable{

    private static final int NODES_THRES_DOWN = 5;

    private Regulator owner;

    private Socket connection;

    private Request request;

    public ConnectionService(Regulator owner, Socket connection){
        this.owner = owner;
        this.connection = connection;
    }



    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(this.connection.getInputStream());
            Request request = (Request)ois.readObject();
            PublicKey publicKey = RSA.getPublicKeyFromString(request.getParams().get().get(0));
            if (RSA.verify(Request.CONNECT,request.getServiceType(),publicKey)){
                ObjectOutputStream oos = new ObjectOutputStream(this.connection.getOutputStream());
                //Connect to DB and return relative account
                Account account = new Account();
                oos.writeObject(1);
                oos.flush();
                oos.writeObject(account);
                oos.flush();
                //Add IP to advertised peers
                this.owner.addPeer(this.connection.getInetAddress());
                //Update peers Service
                int nodesKept = this.owner.getAdvertisedPeers().size();
            int peersToSend = nodesKept > NODES_THRES_DOWN ? (int) (0.1 * nodesKept) : nodesKept;
            oos.writeObject(peersToSend);
            oos.flush();
            for (int i = 0; i < peersToSend; i++) {
                oos.writeObject(this.owner.getAdvertisedPeers().values().toArray()[i]);
                oos.flush();
            }
            oos.close();
        }
            else {
                this.connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
