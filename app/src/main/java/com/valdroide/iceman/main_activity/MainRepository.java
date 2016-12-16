package com.valdroide.iceman.main_activity;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

/**
 * Created by LEO on 2/12/2016.
 */
public interface MainRepository {
    void getClients();
    void saveSale(IceSale sale);
    void saveClient(String client);
}
