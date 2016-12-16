package com.valdroide.iceman.main_activity.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.valdroide.iceman.IceManApp;
import com.valdroide.iceman.R;
import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.main_activity.MainPresenter;
import com.valdroide.iceman.main_activity.adapters.AdapterSpinnerClient;
import com.valdroide.iceman.main_activity.dialog.DialogClient;
import com.valdroide.iceman.reports.iu.Report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ActivityView {

    @Bind(R.id.spinnerClient)
    Spinner spinnerClient;
    @Bind(R.id.spinnerQuantity)
    Spinner spinnerQuantity;
    @Bind(R.id.editTextAmount)
    EditText editTextAmountt;
    @Bind(R.id.buttonSave)
    Button buttonSave;
    @Bind(R.id.mainContent)
    RelativeLayout mainContent;
    @Bind(R.id.buttonDate)
    Button buttonDate;

    ArrayAdapter<String> spinnnerQuantityAdapter;
    ArrayAdapter<String> inicialAdapter;
    DialogClient dialogClient = null;

    List<Client> clientList = null;
    AdapterSpinnerClient adapter;
    @Inject
    MainPresenter presenter;
    Client client;
    IceSale iceSale;
    @Bind(R.id.adView)
    AdView mAdView;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private Calendar calendar = Calendar.getInstance();
    private AdRequest adRequest;
    private InterstitialAd mInterstitialAd;
    private int conteo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupInjection();
        presenter.onCreate();
        getClients();
        InterstitialAd();
        BannerAd();

    }

    private void setupInjection() {
        IceManApp app = (IceManApp) this.getApplication();
        app.getMainComponent(this, this, this).inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    public void fillSinners() {
        if (this.clientList != null)
            if (this.clientList.size() > 0) {
                adapter = new AdapterSpinnerClient(MainActivity.this, R.layout.simple_spinner_dropdown_item, clientList);
                spinnerClient.setAdapter(adapter);
            } else {
                inicialAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.clientInit));
                //    inicialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClient.setAdapter(inicialAdapter);
            }

        spinnnerQuantityAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.quantityArray));
        //   spinnnerQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuantity.setAdapter(spinnnerQuantityAdapter);

    }

    @Override
    public void saveSale(IceSale sale) {
        presenter.saveSale(iceSale);
    }

    @OnClick(R.id.buttonSave)
    public void fillIceSale() {

        if (spinnerClient.getSelectedItem().toString().equals(getResources().getString(R.string.clientInit).trim())) {
            client = new Client();
            client.setID_CLIENT(0);
        } else
            client = (Client) spinnerClient.getSelectedItem();

        String amount = editTextAmountt.getText().toString();
        if (amount.isEmpty())
            amount = "0";

        String fecha = buttonDate.getText().toString();
        if (fecha != null && !fecha.equals("") && !fecha.equals("Fecha")) {
            fecha = fecha.replace("-", "");
        }
        iceSale = new IceSale(0, client.getID_CLIENT(), Integer.parseInt(spinnerQuantity.getSelectedItem().toString()), Integer.parseInt(amount), fecha);

        saveSale(iceSale);
    }

    @Override
    public void onAddComplete(String msg) {
        Snackbar.make(mainContent, msg, Snackbar.LENGTH_SHORT).show();
        getClients();
        conteoClick();
        buttonDate.setText("Fecha");
        editTextAmountt.setText("");
    }

    @Override
    public void onAddError(String error) {
        Snackbar.make(mainContent, error, Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void saveClient(String client) {
        presenter.saveClient(client);
        dialogClient.alertDialog.dismiss();
    }

    @OnClick(R.id.buttonDate)
    public void setDate() {
        new DatePickerDialog(MainActivity.this, d, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void updatedate() {
        buttonDate.setText(formate.format(calendar.getTime()));
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

    public void showDialog() {
        dialogClient = new DialogClient(this);
        dialogClient.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String client = dialogClient.editTextClient.getText().toString();
                saveClient(client);

            }
        });
        dialogClient.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClient.alertDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_client) {
            showDialog();
            return true;
        }
        if (id == R.id.report) {
            startActivity(new Intent(MainActivity.this, Report.class));
            return true;
        }
        return true;
    }

    public void InterstitialAd() {
        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        adRequest = new AdRequest.Builder()
                  //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //   .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mInterstitialAd.loadAd(adRequest);

    }

    public void BannerAd() {

        adRequest = new AdRequest.Builder()
             //     .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //      .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
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
