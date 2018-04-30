package com.switchfully.vaadin.ordergui.interfaces;

public class PhoneNumber {
    private String number;
    private String countryCallingCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }
}
