package com.sec.secureapp.general;

import java.util.ArrayList;

public class ParticipationInfo {

    private String participant_id;
    private int auction_id;

    public ParticipationInfo(String participant_id, int auction_id) {
        this.participant_id = participant_id;
        this.auction_id = auction_id;
    }

    public ParticipationInfo(){}

    public String getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public int getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(int auction_id) {
        this.auction_id = auction_id;
    }
}
