package auctioneum.network;


import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class Request implements Serializable {

    /** Request types **/

    public static final String REGISTER = "r";
    public static final String CONNECT = "c";
    public static final String UPDATE_PEERS = "u.p";
    public static final String UPDATE_BLOCKCHAIN = "u.bc";
    public static final String GET_AUCTIONS = "a";



    private final String serviceType;

    private Optional<List<String>> params;

    public Request(final String serviceType, Optional<List<String>> params){
        this.serviceType = serviceType;
        if (params.isPresent()){
            this.params = params;
        }

    }

    public String getServiceType() {
        return this.serviceType;
    }

    public Optional<List<String>> getParams() {
        return this.params;
    }

    public void setParams(Optional<List<String>> params) {
        this.params = params;
    }


}
