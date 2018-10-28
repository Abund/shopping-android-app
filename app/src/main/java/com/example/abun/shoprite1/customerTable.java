package com.example.abun.shoprite1;

/**
 * Created by Abun on 7/21/2018.
 */
import java.util.Date;

public class customerTable {

    private int custom;
    private String customerName;
    private String gender;
    private String address;
    private String phone;
    private Date Dob;
    private String password1;
    private int creditCardNo;
    private String creditCardType;

    public customerTable(int customerid,String customerName,String gender,String address,String phone,Date Dob,String password1,
                         int creditCardNo,String creditCardType){
        this.setCustom(customerid);
        this.setCustomerName(customerName);
        this.setGender(gender);
        this.setAddress(address);
        this.setPhone(phone);
        this.setDob(Dob);
        this.setPassword1(password1);
        this.setCreditCardNo(creditCardNo);
        this.setCreditCardType(creditCardType);
    }

    public int getCustom() {
        return custom;
    }

    public void setCustom(int custom) {
        this.custom = custom;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return Dob;
    }

    public void setDob(Date dob) {
        Dob = dob;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public int getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(int creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }
}
