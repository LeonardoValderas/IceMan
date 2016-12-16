package com.valdroide.iceman.main_activity;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

/**
 * Created by LEO on 1/12/2016.
 */
public class MainInteractorImpl implements MainInteractor {
    MainRepository repository;

    public MainInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveSale(IceSale sale) {
        repository.saveSale(sale);
    }

    @Override
    public void getClients() {
        repository.getClients();
    }

    @Override
    public void saveClient(String client) {
        repository.saveClient(client);
    }
}
