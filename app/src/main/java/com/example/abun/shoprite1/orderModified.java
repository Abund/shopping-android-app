package com.example.abun.shoprite1;

/**
 * Created by Abun on 7/21/2018.
 */

public class orderModified {

    private int quantity;
    private int cutid;


    public orderModified(int cust,int qty){
        this.cutid=cust;
        quantity=qty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCutid() {
        return cutid;
    }

    public void setCutid(int cutid) {
        this.cutid = cutid;
    }
}
