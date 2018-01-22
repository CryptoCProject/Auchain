package auctioneum.network;


import auctioneum.blockchain.Account;

import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;

/** Responsible for connections,updates in the network **/
public class Regulator extends Node{

    private Map<Integer,Node> advertisedPeers;

    private Server connectionServer;
    
    private Server registrationServer;
    
    public Regulator(){
        advertisedPeers = new TreeMap<>();
        connectionServer = new Server<ConnectionService>(this,Settings.CONNECTION_PORT,"ConnectionServer",ConnectionService.class);
        registrationServer = new Server<RegisterService>(this,Settings.REGISTRATION_PORT,"RegistrationServer",RegisterService.class);
        Thread connectionService = new Thread(connectionServer);
        Thread registrationService = new Thread(registrationServer);
        registrationService.start();
        connectionService.start();
    }

//    public Regulator(Account account){
//        super(account);
//        advertisedPeers = new TreeMap<>();
//        connectionServer = new Server<ConnectionService>(this,Settings.CONNECTION_PORT,"ConnectionServer",ConnectionService.class);
//        registrationServer = new Server<RegisterService>(this,Settings.REGISTRATION_PORT,"RegistrationServer",RegisterService.class);
//        Thread connectionService = new Thread(connectionServer);
//        Thread registrationService = new Thread(registrationServer);
//        registrationService.start();
//        connectionService.start();
//    }


    public void addPeer(InetAddress ip){
        Node peer = new Node();
        peer.setIp(ip);
        peer.setTransactionsPort(Settings.TRANSACTIONS_PORT);
        peer.setValidationsPort(Settings.VALIDATIONS_PORT);
        this.advertisedPeers.put(0,peer);
    }

    //public void

    public Map<Integer, Node> getAdvertisedPeers() {
        return this.advertisedPeers;
    }

    public void setAdvertisedPeers(Map<Integer, Node> advertisedPeers) {
        this.advertisedPeers = advertisedPeers;
    }
    
//   public void close() {
//       this.connectionServer.stop();
//       this.registrationServer.stop();
//   }

}
