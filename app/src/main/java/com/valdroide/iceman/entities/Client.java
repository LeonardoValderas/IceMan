package com.valdroide.iceman.entities;

/**
 * Created by LEO on 2/12/2016.
 */
public class Client {
    int ID_CLIENT;
    String NAME;

    public Client(int ID_CLIENT, String NAME) {
        this.ID_CLIENT = ID_CLIENT;
        this.NAME = NAME;
    }
    public Client(){}

    public int getID_CLIENT() {
        return ID_CLIENT;
    }

    public void setID_CLIENT(int ID_CLIENT) {
        this.ID_CLIENT = ID_CLIENT;
    }

    public String getNAME() {
        return NAME;
    }
    public String toString() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
