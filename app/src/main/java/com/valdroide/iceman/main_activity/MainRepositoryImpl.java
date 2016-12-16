package com.valdroide.iceman.main_activity;

import android.content.Context;

import com.valdroide.iceman.db.DataBaseController;
import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.lib.base.EventBus;
import com.valdroide.iceman.main_activity.events.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEO on 2/12/2016.
 */
public class MainRepositoryImpl implements MainRepository {
    EventBus eventBus;
    Context context;
    DataBaseController controller;
    List<Client> clientList = new ArrayList<>();

    public MainRepositoryImpl(EventBus eventBus, Context context) {
        this.eventBus = eventBus;
        this.context = context;
    }

    //getclient = 0, complete = 1 error= 2

    @Override
    public void getClients() {
        instanceController(context);
        clientList = controller.selectAllClient();
        post(clientList, ActivityEvent.GET_CLIENT);

    }

    @Override
    public void saveSale(IceSale sale) {

        if (sale.getID_CLIENT() == 0) {
            post("Debe generar un cliente.", ActivityEvent.ADD_ERROR);
        } else if (sale.getAMOUNT() == 0) {
            post("Debe ingresar un monto.", ActivityEvent.ADD_ERROR);
        } else  if (sale.getDATE().equals("") || sale.getDATE().equals("Fecha") ) {
                post("Debe ingresar una fecha.", ActivityEvent.ADD_ERROR);
        }  else {
            instanceController(context);
            if (controller.insertSale(sale))
                post("Venta guardada.", true, ActivityEvent.ADD_COMPLETE);
            else
                post("Error al guardar la venta.", ActivityEvent.ADD_ERROR);
        }
    }

    @Override
    public void saveClient(String client) {
        if (client != null)
            if (client.isEmpty())
                post("Ingrese un cliente.", ActivityEvent.ADD_ERROR);
            else {
                instanceController(context);
                if (controller.insertClient(client))
                    post("Cliente guardado.", true, ActivityEvent.ADD_COMPLETE);
                else
                    post("Error al guardar el cliente.", ActivityEvent.ADD_ERROR);
            }
    }

    public void instanceController(Context context) {
        controller = new DataBaseController(context);
    }

    public void post(String error, String complete, List<Client> list, int type) {
        ActivityEvent event = new ActivityEvent();
        event.setType(type);
        event.setClientList(list);
        event.setError(error);
        event.setComplete(complete);
        eventBus.post(event);
    }

    public void post(String error, int type) {
        post(error, null, null, type);
    }

    public void post(String complete, boolean dato, int type) {
        post(null, complete, null, type);
    }

    public void post(List<Client> list, int type) {
        post(null, null, list, type);
    }
}
