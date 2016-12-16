package com.valdroide.iceman.main_activity;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

import java.util.List;

/**
 * Created by LEO on 1/12/2016.
 */
public interface MainInteractor {
    void saveSale(IceSale sale);
    void getClients();
    void saveClient(String client);
}
