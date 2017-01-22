package com.khalilayache.starcode.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.adapters.StarWarsCharListAdapter;
import com.khalilayache.starcode.helpers.DeviceHelper;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.utils.ApiUtils;
import com.khalilayache.starcode.utils.SQLUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION = 958;
    private static final int QRCODE_INTENT = 674;
    private static final int CHAR_LOADER_ID = 257;

    private String apiURL;

    private StarWarsCharsAsyncTask starWarsTask;

    private Class<?> mClss;
    private ArrayList<StarWarsChar> charArrayList;

    private TextView emptyStateTextView;
    private StarWarsCharListAdapter adapter;
    private View loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView = (TextView) findViewById(R.id.empty_view);

        ListView starWarsCharsListView = (ListView) findViewById(R.id.list);
        SQLUtils db = new SQLUtils(getApplicationContext());
        charArrayList = db.getAllStarWarsChars();
        adapter = new StarWarsCharListAdapter(this, charArrayList );

        starWarsCharsListView.setAdapter(adapter);
        starWarsCharsListView.setEmptyView(emptyStateTextView);
        emptyStateTextView.setText(R.string.no_chars_registered);

    }

    public void launchScannerActivity(View v) {

        if(DeviceHelper.checkInternetConnection(getApplicationContext())) {
            launchActivity(ScannerActivity.class);
        }else{
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
        }
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
                if(DeviceHelper.checkInternetConnection(getApplicationContext())) {
                    loadingIndicator.setVisibility(View.VISIBLE);
                    emptyStateTextView.setVisibility(View.GONE);

                    apiURL = data.getStringExtra("url");
                    starWarsTask = new StarWarsCharsAsyncTask();
                    starWarsTask.execute(apiURL);
                }else{
                    Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                }

            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Action cancelled", Toast.LENGTH_SHORT).show();
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




    //region Star Wars Chars AsyncTask
    private class StarWarsCharsAsyncTask extends AsyncTask<String, Void, StarWarsChar> {

        @Override
        protected StarWarsChar doInBackground(String... urls) {
            if(apiURL == null){
                return null;
            }

            return ApiUtils.fetchCharData(apiURL);
        }

        @Override
        protected void onPostExecute(StarWarsChar starWarsChar) {
            SQLUtils db = new SQLUtils(getApplicationContext());
            StarWarsChar checkChar =  db.getStarWarsChar(starWarsChar);

            if(checkChar.getName() == null) {
                charArrayList.add(starWarsChar);
                if (charArrayList != null & !charArrayList.isEmpty()) {
                    adapter.notifyDataSetChanged();
                    loadingIndicator.setVisibility(View.GONE);
                    emptyStateTextView.setVisibility(View.GONE);
                    db.insertStarWarsChar(starWarsChar);
                }
            }else{
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, R.string.chars_already_registered, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //endregion
}
