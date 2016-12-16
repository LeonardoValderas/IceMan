package com.valdroide.iceman.reports.iu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.valdroide.iceman.IceManApp;
import com.valdroide.iceman.R;
import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.reports.ReportPresenter;
import com.valdroide.iceman.reports.adapters.AdapterReports;
import com.valdroide.iceman.reports.adapters.AdapterSpinnerClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Report extends AppCompatActivity implements ReportView {

    @Bind(R.id.buttonDateSince)
    Button buttonDateSince;
    @Bind(R.id.buttonDateUntil)
    Button buttonDateUntil;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.spinnerC)
    Spinner spinnerC;


    @Inject
    AdapterReports adapter;
    @Inject
    ReportPresenter presenter;
    @Bind(R.id.mainContent)
    RelativeLayout mainContent;
    @Bind(R.id.quantity_total)
    TextView quantityTotal;
    @Bind(R.id.amount_total)
    TextView amountTotal;
    @Bind(R.id.adView)
    AdView mAdView;
    @Bind(R.id.Linear2)
    LinearLayout Linear2;


    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private Calendar calendar = Calendar.getInstance();
    boolean isSince = true;
    AdapterSpinnerClient adapterClient;
    ArrayAdapter<String> inicialAdapter;
    List<Client> clientList = null;
    List<IceSale> iceList = null;
    private AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    private int conteo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        setupInjection();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter.onCreate();
        setupRecyclerView();
        getClients();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InterstitialAd();
        BannerAd();

    }

    private void setupInjection() {
        IceManApp app = (IceManApp) this.getApplication();
        app.getReportComponent(this, this, this).inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(Report.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void fillSinners() {
        if (this.clientList != null)
            if (this.clientList.size() > 0) {
                adapterClient = new AdapterSpinnerClient(Report.this, R.layout.simple_spinner_dropdown_item, clientList);
                spinnerC.setAdapter(adapterClient);
            } else {
                inicialAdapter = new ArrayAdapter<>(Report.this, R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.clientInit));
                //    inicialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerC.setAdapter(inicialAdapter);
            }
    }

    @Override
    public void listEmpty(String empty) {
        Snackbar.make(mainContent, empty, Snackbar.LENGTH_SHORT).show();
        adapter.setChecks(new ArrayList<IceSale>());
        setTextViewTotal(0, 0);
    }

    @Override
    public void error(String error) {
        Snackbar.make(mainContent, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setListSale(List<IceSale> sales) {
        this.iceList = sales;
        adapter.setChecks(iceList);
        setTextViewTotal(iceList.get(0).getQUANTITY_TOTAL(), iceList.get(0).getAMOUNT_TOTAL());
        conteoClick();
    }

    public void setTextViewTotal(int q, int a) {
        quantityTotal.setText(String.valueOf(q));
        amountTotal.setText("$ " + String.valueOf(a));
    }

    @Override
    public void getClients() {
        presenter.getClient();
    }

    @Override
    public void setClients(List<Client> clientList) {
        if (clientList != null)
            this.clientList = clientList;
        fillSinners();
    }

    @OnClick(R.id.fab)
    public void getListSale() {

        String since = buttonDateSince.getText().toString();
        String until = buttonDateUntil.getText().toString();
        int id_client = 0;
        boolean isAll = false;

        if (since != null && !since.equals("") && !since.equals("Fecha")) {
            since = since.replace("-", "");
            since = formatDate(since);
        }
        if (until != null && !until.equals("") && !until.equals("Fecha")) {
            until = until.replace("-", "");
            until = formatDate(until);
        }

        if (spinnerC.getSelectedItem().toString().equals(getResources().getString(R.string.clientAll).trim())) {
            isAll = true;
        } else if (spinnerC.getSelectedItem().toString().equals(getResources().getString(R.string.clientInit).trim())) {
            id_client = 0;
        } else {
            Client client = (Client) spinnerC.getSelectedItem();
            id_client = client.getID_CLIENT();
        }
        presenter.getListSale(since, until, id_client, isAll);
    }

    @OnClick(R.id.buttonDateSince)
    public void setDateSince() {
        new DatePickerDialog(Report.this, d, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
        isSince = true;
    }

    @OnClick(R.id.buttonDateUntil)
    public void setDateUntil() {
        new DatePickerDialog(Report.this, d, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
        isSince = false;
    }

    public void updatedate() {
        if (isSince)
            buttonDateSince.setText(formate.format(calendar.getTime()));
        else
            buttonDateUntil.setText(formate.format(calendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedate();
        }
    };

    public String formatDate(String date) {
        String day = "";
        String month = "";
        String year = "";
        String newDate = "";
        for (int i = 0; i < date.length(); i++) {

            if (i >= 0 && i <= 1) {
                day += date.charAt(i);
            } else if (i >= 2 && i <= 3) {
                month += date.charAt(i);
            } else {
                year += date.charAt(i);
            }
        }

        return (year + month + day).trim();
    }
    public void InterstitialAd() {
        mInterstitialAd = new InterstitialAd(Report.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen_2));

        adRequest = new AdRequest.Builder()
           //     .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
             //   .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mInterstitialAd.loadAd(adRequest);

    }

    public void BannerAd() {
        adRequest = new AdRequest.Builder()
             //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //    .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void conteoClick() {
        if (conteo == 3) {
            showInterstitial();
            conteo = 0;
        } else {
            conteo++;
        }
    }

}
