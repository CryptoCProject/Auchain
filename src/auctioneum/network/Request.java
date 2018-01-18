package auctioneum.network;


import java.io.Serializable;

public class Request implements Serializable {

    /** Request types **/

    public static final String CONNECT = "c";
    public static final String UPDATE_PEERS = "u.p";
    public static final String UPDATE_BLOCKCHAIN = "u.bc";
    public static final String GET_AUCTIONS = "a";
    private final String service;

    public Request(final String service){
    this.service = service;}


}
