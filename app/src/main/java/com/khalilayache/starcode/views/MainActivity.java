package com.khalilayache.starcode.views;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.adapters.StarWarsCharListAdapter;
import com.khalilayache.starcode.models.StarWarsChar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION = 958;
    private static final int QRCODE_INTENT = 674;
    private static final int CHAR_LOADER_ID = 257;

    private Class<?> mClss;

    private TextView emptyStateTextView;
    private StarWarsCharListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyStateTextView = (TextView) findViewById(R.id.empty_view);

        ListView starWarsCharsListView = (ListView) findViewById(R.id.list);

        adapter = new StarWarsCharListAdapter(this, new ArrayList<StarWarsChar>());

        starWarsCharsListView.setAdapter(adapter);
        starWarsCharsListView.setEmptyView(emptyStateTextView);

    }

    public void launchScannerActivity(View v) {
        launchActivity(ScannerActivity.class);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivityForResult(intent,QRCODE_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == QRCODE_INTENT){
            if(resultCode == RESULT_OK){

                Toast.makeText(this, "String recebida = " + data.getStringExtra("url"), Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Ação cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivityForResult(intent,QRCODE_INTENT);
                    }
                } else {
                    Toast.makeText(this, R.string.access_camera_not_grant, Toast.LENGTH_SHORT).show();
                }
        }
    }

}
