package com.valdroide.iceman.reports.events;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

import java.util.List;

/**
 * Created by LEO on 1/12/2016.
 */
public class ReportEvent {
    private int type;
    private String error;
    private String empty;
    private List<IceSale> saleList;
    private List<Client> clientList;
    public final static int GET_SALE = 0;
    public final static int ADD_ERROR = 1;
    public final static int ADD_EMPTY = 2;
    public final static int ADD_CLIENT = 3;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public List<IceSale> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<IceSale> saleList) {
        this.saleList = saleList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }
}
