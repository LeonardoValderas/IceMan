package com.valdroide.iceman.main_activity.events;

import com.valdroide.iceman.entities.Client;

import java.util.List;

/**
 * Created by LEO on 1/12/2016.
 */
public class ActivityEvent {
    private int type;
    private String error;
    private String complete;
    private List<Client> clientList;
    public final static int GET_CLIENT = 0;
    public final static int ADD_COMPLETE = 1;
    public final static int ADD_ERROR = 2;
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

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }
}
