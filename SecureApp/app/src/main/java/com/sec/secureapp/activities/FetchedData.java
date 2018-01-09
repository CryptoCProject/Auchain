package com.sec.secureapp.activities;

public class FetchedData {

    String auction = "";

    public String getAuction() {
        return auction;
    }

    public void setAuction(String auction) {
        this.auction = auction;
        System.out.println("AuctionSetter"+auction);
    }
}
