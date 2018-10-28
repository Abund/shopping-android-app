package com.example.abun.shoprite1;

import java.sql.Date;

/**
 * Created by Abun on 7/28/2018.
 */

public class employeeTable {
    private int empid;
    private String empName;
    private String empDesignation;
    private String gender;
    private String Address;
    private String email;
    private String phone;
    private Date dateOfBirth;

    public employeeTable(int id, String empName,String designation,String gender,String Address,String email,String phone,Date dob){
        this.setEmpid(id);
        this.setEmpName(empName);
        this.setEmpDesignation(designation);
        this.setGender(gender);
        this.setAddress(Address);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDateOfBirth(dob);
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
