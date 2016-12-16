package com.valdroide.iceman.reports;

/**
 * Created by LEO on 3/12/2016.
 */
public class ReportInteractorImpl implements ReportInteractor {
     ReportRepository repository;

    public ReportInteractorImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getListSale(String since, String until, int id, boolean isAll) {
        repository.getListSale(since, until, id, isAll);
    }

    @Override
    public void getClients() {
        repository.getClients();
    }

}
