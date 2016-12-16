package com.valdroide.iceman.reports;

import android.content.Context;

import com.valdroide.iceman.db.DataBaseController;
import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.lib.base.EventBus;
import com.valdroide.iceman.main_activity.events.ActivityEvent;
import com.valdroide.iceman.reports.events.ReportEvent;
import com.valdroide.iceman.reports.iu.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEO on 3/12/2016.
 */
public class ReportRepositoryImpl implements ReportRepository {
    EventBus eventBus;
    Context context;
    DataBaseController controller;
    List<IceSale> saleList = new ArrayList<>();
    List<Client> clientList = new ArrayList<>();


    public ReportRepositoryImpl(EventBus eventBus, Context context) {
        this.eventBus = eventBus;
        this.context = context;
    }

    @Override
    public void getClients() {
        instanceController(context);
        Client client = new Client(0, "TODOS");

        clientList = controller.selectAllClient();
        clientList.add(0, client);
        post(clientList, ReportEvent.ADD_CLIENT);
    }

    public void instanceController(Context context) {
        controller = new DataBaseController(context);
    }

    public void post(String error, String empty, List<Client> list, List<IceSale> listSale, int type) {
        ReportEvent event = new ReportEvent();
        event.setType(type);
        event.setClientList(list);
        event.setSaleList(listSale);
        event.setError(error);
        event.setEmpty(empty);
        eventBus.post(event);
    }

    public void post(String error, int type) {
        post(error, null, null, null, type);
    }

    public void post(String empty, boolean dato, int type) {
        post(null, empty, null, null, type);
    }

    public void post(List<Client> list, int type) {
        post(null, null, list, null, type);
    }

    public void post(List<IceSale> list, int type, boolean is) {
        post(null, null, null, list, type);
    }

    @Override
    public void getListSale(String since, String until, int id, boolean isAll) {
        instanceController(context);
        if (id == 0 && isAll == false) {
            post("Debe generar un cliente.", ReportEvent.ADD_ERROR);
        } else if (since.equals("") || since.equals("Desde")) {
            post("Debe ingresar una fecha.(Desde)", ReportEvent.ADD_ERROR);
        } else if (until.equals("") || until.equals("Hasta")) {
            post("Debe ingresar una fecha.(Hasta)", ReportEvent.ADD_ERROR);
        } else {
            List<IceSale> listSale = new ArrayList<>();
            if (isAll) {
                listSale = controller.selectSales(since, until);
                if (listSale == null)
                    post("Error al realizar la consulta.", ReportEvent.ADD_ERROR);
                else if (listSale.size() == 0)
                    post("Consulta sin datos.", true, ReportEvent.ADD_EMPTY);
                else
                    post(listSale, ReportEvent.GET_SALE, true);
            } else {
                listSale = controller.selectSales(since, until, id);
                if (listSale == null)
                    post("Error al realizar la consulta.", ReportEvent.ADD_ERROR);
                else if (listSale.size() == 0)
                    post("Consulta sin datos.", true, ReportEvent.ADD_EMPTY);
                else
                    post(listSale, ReportEvent.GET_SALE, true);
            }
        }
    }
}
