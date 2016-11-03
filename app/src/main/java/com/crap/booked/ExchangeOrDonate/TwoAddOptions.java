package com.crap.booked.ExchangeOrDonate;

/**
 * Created by Rashi on 31-08-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crap.booked.R;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;


public class TwoAddOptions extends AppCompatActivity {
    ImageButton manuald , barcoded ;
    String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_add_options);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Share");

        manuald= (ImageButton) findViewById(R.id.manualdetailsbt);
        barcoded= (ImageButton) findViewById(R.id.barcodebt);

        Bundle extras = getIntent().getExtras();
        final String value = extras.getString("E/D");

        manuald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        /*        Intent i = new Intent(TwoAddOptions.this , EnterDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("E/D", value);
                startActivity(i);
        */
                new MaterialDialog.Builder(v.getContext())
                        .title("yoyooyo")
                        .inputRangeRes(10, 13, R.color.colorAccent)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Intent intent = new Intent(getBaseContext(), EnterDetails.class);
                                intent.putExtra("ABCD", scanResult);
                                Intent i =getIntent();
                                String ed = i.getStringExtra("ED");
                                intent.putExtra("ED", ed);
                                startActivity(intent);
                            }
                        }).show();

            }
        });


        barcoded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplication();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){

                    /*
                    Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("E/D", value);
                    startActivity(intent);
               */

                    final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                            .withActivity(TwoAddOptions.this)
                            .withEnableAutoFocus(true)
                            .withBleepEnabled(true)
                            .withBackfacingCamera()
                            .withBarcodeFormats(Barcode.EAN_13)
                            .withText("Scanning...")
                            .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                @Override
                                public void onResult(Barcode barcode) {

                                    Log.d("Scan","Scann");
                                    scanResult=barcode.rawValue;
                                    Log.d("ISBN",scanResult);
                                    Intent intent = new Intent(getBaseContext(), EnterDetails.class);
                                    intent.putExtra("ABCD", scanResult);

                                    intent.putExtra("ED", value);

                                    startActivity(intent);
                                }
                            })
                            .withCenterTracker()

                            .build();
                    materialBarcodeScanner.startScan();
                }
                else{
                    Toast.makeText(getApplication(), "Connect To Internet", Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}

