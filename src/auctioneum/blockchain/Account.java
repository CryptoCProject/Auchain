package auctioneum.blockchain;

import java.util.List;

public class Account {

    /** Signifies the number of transactions made by this account **/
    private int nonce;

    /** Public identified of the account **/
    private String address;

    /** Account balance **/
    private float balance;

    /** Pending transactions **/
    List<Transaction> pendingTxs;

    public Account(){}


    static float getBalance(String address) {
        return 0.0F; //TODO: connect to DB and return balance for this address
    }





    /**--------------Accessors-Mutators-------------------**/

    public int getNonce() { return this.nonce; }

    public void setNonce(int nonce) { this.nonce = nonce; }

    public String getAddress() { return this.address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<Transaction> getPendingTxs() { return this.pendingTxs; }

    public void setPendingTxs(List<Transaction> pendingTxs) { this.pendingTxs = pendingTxs; }
}
