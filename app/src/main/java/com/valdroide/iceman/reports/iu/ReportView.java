package com.valdroide.iceman.reports.iu;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;

import java.util.List;

/**
 * Created by LEO on 3/12/2016.
 */
public interface ReportView {
    void listEmpty(String empty);
    void error(String error);
    void setListSale(List<IceSale> sales);
    void getClients();
    void setClients(List<Client> clientList);
}
