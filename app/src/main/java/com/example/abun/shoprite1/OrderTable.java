package com.example.abun.shoprite1;

/**
 * Created by Abun on 7/18/2018.
 */

public class OrderTable {
    private int orderid;
    private int customerid;
    private int employeeid;
    private int productid;
    private int qty;
    private double amount;
    public OrderTable(int orderID,int customerID,int employeeID,int productID,int qty,double amount){
        this.setOrderid(orderID);
        this.setCustomerid(customerID);
        this.setEmployeeid(employeeID);
        this.setProductid(productID);
        this.setQty(qty);
        this.setAmount(amount);
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
