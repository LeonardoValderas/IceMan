package com.valdroide.iceman.main_activity.ui;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

import java.util.List;

/**
 * Created by LEO on 1/12/2016.
 */
public interface ActivityView {
    void saveSale(IceSale sale);
    void onAddComplete(String msg);
    void onAddError(String error);
    void getClients();
    void setClients(List<Client> clientList);
    void saveClient(String client);
}
