/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctioneum.smartcontract;

import java.util.List;
import sslserver.User;

/**
 *
 * @author panagiotisspentzouris
 */
public class EnglishAuction implements Auction{


    /** Item to be sold **/
    private Item item; // TODO: Implement class Item
    
    /** Starting price **/
    private int reservePrice;
    
    /** Participants of the Auction **/
    private List<User> participants;
    
    /** Current highest bid **/
    private double high_bid = 0;
   
    
    
    public EnglishAuction(){}
    
    public void run(){
        
    }
    
    public void setHighestBid(Double bid){
        this.high_bid = bid;
    }
    
    public double getHighestBid(){
        return this.high_bid;
    }
}
