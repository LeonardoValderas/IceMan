package com.valdroide.iceman.main_activity.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.valdroide.iceman.R;

/**
 * Created by LEO on 2/12/2016.
 */
public class DialogClient {

    public Context context;
    public Button btnAceptar = null;
    public Button btnCancelar = null;
    public AlertDialog alertDialog;
    public EditText editTextClient;

    public DialogClient(Context context) {
        this.context = context;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_client, null);

        // TITULO
        editTextClient = (EditText) layout.findViewById(R.id.editTextClient);
        // BOTON ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        // BOTON CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
