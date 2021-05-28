package com.mohammadkz.bimix.Model;

import com.google.gson.annotations.SerializedName;

public class InsuranceResponse {

    @SerializedName("kind")
    String kind;
    @SerializedName("date")
    String date;
    @SerializedName("trackingCode")
    String trackingCode;
    @SerializedName("link")
    String link;
    @SerializedName("payCode")
    String payCode;
    @SerializedName("result")
    String status;
    @SerializedName("payStatus")
    private String payed;

    public InsuranceResponse(String kind, String date, String trackingCode, String link, String payCode, String status) {
        this.kind = kind;
        this.date = date;
        this.trackingCode = trackingCode;
        this.link = link;
        this.payCode = payCode;
        this.status = status;
    }

    public InsuranceResponse() {

    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
