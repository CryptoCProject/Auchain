package auctioneum.network;

import auctioneum.blockchain.Account;
import auctioneum.blockchain.Block;
import auctioneum.blockchain.Transaction;
import auctioneum.utils.hashing.SHA3_256;

import java.math.BigInteger;
import java.util.List;

public class Miner extends Node {

    private int difficulty;

    private boolean stop;

    public Miner(Account account){
        super(account, Settings.IP);
        this.stop = false;
    }


    public Block createBlock(int size, int difficulty){
        int number = this.getBlockChain().size()+1;
        String selfAddress = this.getAccount().getAddress();
        List<Transaction> txsIncluded = this.getTxPool().subList(0,size);
        return new Block(BigInteger.ZERO,number,difficulty,selfAddress,txsIncluded);
    }



    private Block mine(Block block){
        while (!this.stop && this.count("0", SHA3_256.hash(block.getData())) != difficulty){
            block.setNonce(block.getNonce().add(BigInteger.ONE));
        }
        return block;
    }

    public void start(){}

    public void stop(){ this.stop = true; }

    private int count(final String string, final String substring){
        int count = 0;
        int idx = 0;

        while ((idx = string.indexOf(substring, idx)) != -1)
        {
            idx++;
            count++;
        }

        return count;
    }


}
