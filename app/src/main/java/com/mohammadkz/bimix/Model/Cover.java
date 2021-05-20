package com.mohammadkz.bimix.Model;

public class Cover {
    private String text, describe;
    private boolean expended, checked;

    public Cover() {
        this.expended = false;
        this.checked = false;
    }

    public Cover(String text) {
        this.text = text;
        this.expended = expended;
        this.checked = checked;
    }

    public Cover(String text , String describe) {
        this.text = text;
        this.expended = expended;
        this.checked = checked;
        this.describe = describe;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
