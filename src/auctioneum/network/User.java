package auctioneum.network;

import auctioneum.blockchain.Account;
import auctioneum.blockchain.Transaction;

import java.net.InetAddress;
import java.util.List;

public class User extends Node {


    public User(Account account, InetAddress ip){
        super(account,ip);
    }

    public void signTransaction(Transaction transaction){}




}
