package com.valdroide.iceman.entities;

/**
 * Created by LEO on 1/12/2016.
 */
public class IceSale {
    int ID_SALE;
    int ID_CLIENT;
    int QUANTITY;
    int AMOUNT;
    String DATE;
    String CLIENT;
    int QUANTITY_TOTAL;
    int AMOUNT_TOTAL;

    public IceSale(){};
    public IceSale(int ID_SALE, int ID_CLIENT, int QUANTITY, int AMOUNT, String DATE) {
        this.ID_SALE = ID_SALE;
        this.ID_CLIENT = ID_CLIENT;
        this.QUANTITY = QUANTITY;
        this.AMOUNT = AMOUNT;
        this.DATE = DATE;
    }

    public IceSale(int QUANTITY_TOTAL, int AMOUNT_TOTAL, String CLIENT, int QUANTITY, int AMOUNT, String DATE) {
        this.QUANTITY_TOTAL = QUANTITY_TOTAL;
        this.AMOUNT_TOTAL = AMOUNT_TOTAL;
        this.CLIENT = CLIENT;
        this.QUANTITY = QUANTITY;
        this.AMOUNT = AMOUNT;
        this.DATE = DATE;
    }

    public int getID_SALE() {
        return ID_SALE;
    }

    public void setID_SALE(int ID_SALE) {
        this.ID_SALE = ID_SALE;
    }

    public int getID_CLIENT() {
        return ID_CLIENT;
    }

    public void setID_CLIENT(int ID_CLIENT) {
        this.ID_CLIENT = ID_CLIENT;
    }

    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public int getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(int AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getCLIENT() {
        return CLIENT;
    }

    public void setCLIENT(String CLIENT) {
        this.CLIENT = CLIENT;
    }

    public int getQUANTITY_TOTAL() {
        return QUANTITY_TOTAL;
    }

    public void setQUANTITY_TOTAL(int QUANTITY_TOTAL) {
        this.QUANTITY_TOTAL = QUANTITY_TOTAL;
    }

    public int getAMOUNT_TOTAL() {
        return AMOUNT_TOTAL;
    }

    public void setAMOUNT_TOTAL(int AMOUNT_TOTAL) {
        this.AMOUNT_TOTAL = AMOUNT_TOTAL;
    }
}
