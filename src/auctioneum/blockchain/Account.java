package auctioneum.blockchain;

public class Account {

    /** Signifies the number of transactions made by this account **/
    private int nonce;

    /** Public identified of the account **/
    private String address;

    /** Account balance **/
    private float balance;

    public Account(){}


    static float getBalance(String address) {
        return 0.0F;
    }




    /**--------------Accessors-Mutators-------------------**/

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

}
