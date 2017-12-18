package auctioneum.blockchain;

import java.io.Serializable;
import auctioneum.utils.keys.Verifier;


public class Transaction implements Serializable{

    private static final long serialVersionUID = -1565349679238127979L;

    /** Transaction identifier **/
    private String id;

    /** Receiver's address **/
    private String to;

    /** Sender's address **/
    private String from;

    /** Amount to be transfered **/
    private float value;

    /** Transaction signature **/
    private String signature;

    public Transaction(String id, String from, String to, float value, String signature) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.value = value;
        this.signature = signature;
    }

    /* valid signatures, valid transaction id, balance check */
    public boolean isValid(){
        Verifier v = new Verifier();
       if(v.verifySignature() && Account.getBalance(this.from) >= this.value)
           return true;
       else
           return false;
     }


    /**--------------Accessors-Mutators------------------**/

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    
    public String toString() {
        String info = "";
        info += "ID: "+ this.id;
        info += "\nFrom: "+ this.from;
        info += "\nTo: "+ this.to;
        info += "\nValue: "+ this.value;
        return info;
    }

}
