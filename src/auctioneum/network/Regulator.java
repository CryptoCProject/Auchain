package auctioneum.network;


import auctioneum.blockchain.Account;

import java.util.HashMap;
import java.util.Map;

/** Responsible for connections,updates in the network **/
public class Regulator extends Node{

    private Map<Node,Integer> advertisedPeers;

    public Regulator(Account account){
        super(account);
        advertisedPeers = new HashMap<>();
    }



    public Map<Node, Integer> getAdvertisedPeers() {
        return this.advertisedPeers;
    }

    public void setAdvertisedPeers(Map<Node, Integer> advertisedPeers) {
        this.advertisedPeers = advertisedPeers;
    }





}
