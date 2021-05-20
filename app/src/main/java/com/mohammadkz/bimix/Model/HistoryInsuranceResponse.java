package com.mohammadkz.bimix.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistoryInsuranceResponse {
    String code;

    @SerializedName("")
    List<InsuranceResponse> insuranceResponses;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<InsuranceResponse> getInsuranceResponses() {
        return insuranceResponses;
    }

    public void setInsuranceResponses(List<InsuranceResponse> insuranceResponses) {
        this.insuranceResponses = insuranceResponses;
    }
}
