package com.mohammadkz.bimix.Model;

public class User {
    private String ID, auth, name, password, idCardImage, birthdayDate, phoneNumber, idCard , point;

    public User(String ID, String auth, String name, String phoneNumber) {
        this.ID = ID;
        this.auth = auth;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    public User(String name, String password, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdCardImage() {
        return idCardImage;
    }

    public void setIdCardImage(String idCardImage) {
        this.idCardImage = idCardImage;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


}
