package com.valdroide.iceman.reports.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valdroide.iceman.R;
import com.valdroide.iceman.entities.IceSale;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LEO on 3/12/2016.
 */
public class AdapterReports extends RecyclerView.Adapter<AdapterReports.ViewHolder> {


    private List<IceSale> saleList;
    private Context context;

    public AdapterReports(List<IceSale> saleList, Context context) {
        this.saleList = saleList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IceSale sale = saleList.get(position);

        holder.textViewDate.setText(setDateFormat(sale.getDATE()));
        holder.textViewClient.setText(sale.getCLIENT());
        holder.textViewQuantity.setText(String.valueOf(sale.getQUANTITY()));
        holder.textViewAmount.setText("$ " + String.valueOf(sale.getAMOUNT()));
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }

    public void setChecks(List<IceSale> sales) {
        this.saleList = sales;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textViewDate)
        TextView textViewDate;
        @Bind(R.id.textViewClient)
        TextView textViewClient;
        @Bind(R.id.textViewQuantity)
        TextView textViewQuantity;
        @Bind(R.id.textViewAmount)
        TextView textViewAmount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public String setDateFormat(String date) {

        String newDate ="";
        for (int i = 0; i < date.length(); i++) {

            if (i == 2 || i == 4) {
                newDate += "-";
            }
            newDate += date.charAt(i);
        }
        return newDate;
    }
}
