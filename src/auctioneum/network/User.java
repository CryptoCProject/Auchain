package auctioneum.network;

import auctioneum.blockchain.Account;
import auctioneum.blockchain.Transaction;

import java.net.InetAddress;

public class User extends Node {


    public User(Account account){
        super(account);
    }

    public void signTransaction(Transaction transaction){}




}
