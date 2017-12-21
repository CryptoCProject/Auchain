package auctioneum.network;


import auctioneum.blockchain.Account;
import auctioneum.blockchain.Block;

import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Responsible for connections,updates in the network **/
public class Regulator extends Node{

    private Map<Integer,Node> advertisedPeers;

    public Regulator(Account account){
        super(account);
        advertisedPeers = new TreeMap<>();
    }



    public Map<Integer, Node> getAdvertisedPeers() {
        return this.advertisedPeers;
    }

    public void setAdvertisedPeers(Map<Integer, Node> advertisedPeers) {
        this.advertisedPeers = advertisedPeers;
    }





}
