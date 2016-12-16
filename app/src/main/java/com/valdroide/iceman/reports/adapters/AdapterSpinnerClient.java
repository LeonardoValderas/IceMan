package com.valdroide.iceman.reports.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.valdroide.iceman.R;
import com.valdroide.iceman.entities.Client;

import java.util.List;

/**
 * Created by LEO on 2/12/2016.
 */
public class AdapterSpinnerClient extends ArrayAdapter<Client> {

    private Activity context;
    List<Client> clientArray = null;

    public AdapterSpinnerClient(Activity context, int resource, List<Client> clientArray) {
        super(context, resource, clientArray);
        this.context = context;
        this.clientArray = clientArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.item_spinner, parent, false);
        }

        Client client = clientArray.get(position);

        if(client != null)
        {   // Parse the data from each object and set it.
            TextView spinnerGeneral = (TextView) row.findViewById(R.id.textViewDescrption);
            if(spinnerGeneral != null)
                spinnerGeneral.setText(client.getNAME());
        }

        return row;
    }


}
