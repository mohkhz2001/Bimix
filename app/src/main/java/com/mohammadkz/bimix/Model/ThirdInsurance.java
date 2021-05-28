package com.mohammadkz.bimix.Model;

public class ThirdInsurance {

    private String useFor, lastCompany, insuranceID, endDate, trackingCode, date;
    private String insurancePic, onCarCardPic, backCarCardPic, onCertificatePic, backCertificatePic;
    private String payed;

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed;
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

    public String getUseFor() {
        return useFor;
    }

    public void setUseFor(String useFor) {
        this.useFor = useFor;
    }

    public String getLastCompany() {
        return lastCompany;
    }

    public void setLastCompany(String lastCompany) {
        this.lastCompany = lastCompany;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInsurancePic() {
        return insurancePic;
    }

    public void setInsurancePic(String insurancePic) {
        this.insurancePic = insurancePic;
    }

    public String getOnCarCardPic() {
        return onCarCardPic;
    }

    public void setOnCarCardPic(String onCarCardPic) {
        this.onCarCardPic = onCarCardPic;
    }

    public String getBackCarCardPic() {
        return backCarCardPic;
    }

    public void setBackCarCardPic(String backCarCardPic) {
        this.backCarCardPic = backCarCardPic;
    }

    public String getOnCertificatePic() {
        return onCertificatePic;
    }

    public void setOnCertificatePic(String onCertificatePic) {
        this.onCertificatePic = onCertificatePic;
    }

    public String getBackCertificatePic() {
        return backCertificatePic;
    }

    public void setBackCertificatePic(String backCertificatePic) {
        this.backCertificatePic = backCertificatePic;
    }
}
