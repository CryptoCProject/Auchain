package auctioneum;


import auctioneum.blockchain.Account;
import auctioneum.blockchain.Transaction;
import auctioneum.network.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        try {
            List<Map<String,Object>> nodes = new ArrayList<>();
            nodes.toArray();
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
