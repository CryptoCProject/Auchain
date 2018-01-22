package auctioneum.network;


import auctioneum.blockchain.Account;

import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;

/** Responsible for connections,updates in the network **/
public class Regulator extends Node{

    private Map<Integer,Node> advertisedPeers;

    private int port = 7777;
    
    public Regulator(){}

    public Regulator(Account account){
        super(account);
        advertisedPeers = new TreeMap<>();
    }


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

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }
}
