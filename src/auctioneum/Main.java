package auctioneum;


import auctioneum.blockchain.Account;
import auctioneum.blockchain.Transaction;
import auctioneum.network.Node;

public class Main {

    public static void main(String[] args){
        try {
            Node node = new Node(new Account());
            node.startTxServer();
            node.startVdnServer();
            Transaction tx = new Transaction();
            tx.setId("id1");
            node.sendTransaction(tx,node);
            node.getValidationServer().stop();
            node.getTransactionServer().stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
