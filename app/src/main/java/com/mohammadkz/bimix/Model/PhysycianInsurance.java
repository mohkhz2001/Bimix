package com.mohammadkz.bimix.Model;

public class PhysycianInsurance {
    private String medicalID, specialty, maxLiability, place;
    private boolean paramedical, beautyInsurance, resident, additionalCover;

    public String getMedicalID() {
        return medicalID;
    }

    public void setMedicalID(String medicalID) {
        this.medicalID = medicalID;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getMaxLiability() {
        return maxLiability;
    }

    public void setMaxLiability(String maxLiability) {
        this.maxLiability = maxLiability;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isParamedical() {
        return paramedical;
    }

    public void setParamedical(boolean paramedical) {
        this.paramedical = paramedical;
    }

    public boolean isBeautyInsurance() {
        return beautyInsurance;
    }

    public void setBeautyInsurance(boolean beautyInsurance) {
        this.beautyInsurance = beautyInsurance;
    }

    public boolean isResident() {
        return resident;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    public boolean isAdditionalCover() {
        return additionalCover;
    }

    public void setAdditionalCover(boolean additionalCover) {
        this.additionalCover = additionalCover;
    }
}
