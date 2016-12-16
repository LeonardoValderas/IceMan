package com.valdroide.iceman.reports;

/**
 * Created by LEO on 3/12/2016.
 */
public interface ReportRepository {
    void getListSale(String since, String until, int id, boolean isAll);
    void getClients();
}
