package com.mohammadkz.bimix.Model;

import java.util.ArrayList;

public class BodyInsurance {
    private String useFor, history, year, lastCompany, numberInsurance, carModel, onCarCard, backOnCarCard , trackingCode;
    private ArrayList<String> cover, off;
    private Visit visit;
    private AnotherPerson anotherPerson;

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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLastCompany() {
        return lastCompany;
    }

    public void setLastCompany(String lastCompany) {
        this.lastCompany = lastCompany;
    }

    public String getNumberInsurance() {
        return numberInsurance;
    }

    public void setNumberInsurance(String numberInsurance) {
        this.numberInsurance = numberInsurance;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getOnCarCard() {
        return onCarCard;
    }

    public void setOnCarCard(String onCarCard) {
        this.onCarCard = onCarCard;
    }

    public String getBackOnCarCard() {
        return backOnCarCard;
    }

    public void setBackOnCarCard(String backOnCarCard) {
        this.backOnCarCard = backOnCarCard;
    }

    public ArrayList<String> getCover() {
        return cover;
    }

    public void setCover(ArrayList<String> cover) {
        this.cover = cover;
    }

    public ArrayList<String> getOff() {
        return off;
    }

    public void setOff(ArrayList<String> off) {
        this.off = off;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public AnotherPerson getAnotherPerson() {
        return anotherPerson;
    }

    public void setAnotherPerson(AnotherPerson anotherPerson) {
        this.anotherPerson = anotherPerson;
    }

    public static class Visit {
        private String address, date, time;
        private boolean inPlace;

        public Visit(boolean inPlace) {
            this.inPlace = inPlace;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isInPlace() {
            return inPlace;
        }

        public void setInPlace(boolean inPlace) {
            this.inPlace = inPlace;
        }
    }

    public static class AnotherPerson {
        private String name, idCard, birthdayDate, phoneNumber;
        private boolean use;

        public boolean isUse() {
            return use;
        }

        public void setUse(boolean use) {
            this.use = use;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getBirthdayDate() {
            return birthdayDate;
        }

        public void setBirthdayDate(String birthdayDate) {
            this.birthdayDate = birthdayDate;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
