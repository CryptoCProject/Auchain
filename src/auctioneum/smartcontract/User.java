
package smartcontract;

import auctioneum.blockchain.Account;
import java.io.Serializable;
import java.net.InetAddress;
import auctioneum.network.Channel;
import auctioneum.network.Node;
import auctioneum.network.Request;
import auctioneum.network.Settings;
import java.io.IOException;
import java.util.List;


public class User extends Node implements Serializable{
    
    List <Auction> auctions;
    
    public User() throws IOException, ClassNotFoundException{
        
    }
    
    public void connect(){
        try{
            Request account_request = new Request("c");
            Channel<Account> account_channel = new Channel();
            Request auctions_request = new Request("a");
            Channel<Account> auctions_channel = new Channel();
            this.setAccount(account_channel.getData(account_request, Settings.REGULATOR.getIp(), Settings.REGULATOR.getPort()).get(0));
            this.setAuctions(auctions_channel.getData(auctions_request, Settings.REGULATOR.getIp(), Settings.REGULATOR.getPort()));
        }catch(Exception e){}
    }
    
    private void setAuctions(List auctions){
        this.auctions = auctions;
    }
    
    
}
