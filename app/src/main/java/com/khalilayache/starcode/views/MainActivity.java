package com.khalilayache.starcode.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

    private String apiCharURL;
    private final String API_VALID_URL = "http://swapi.co/api/people/";

    private StarWarsCharsAsyncTask starWarsCharsTask;
    private StarWarsValidURLsAsyncTask starWarsValidURLsTask;

    private Class<?> mClss;
    private ArrayList<StarWarsChar> charArrayList;

    private TextView emptyStateTextView;
    private StarWarsCharListAdapter adapter;
    private View loadingIndicator;

    private FloatingActionButton fabAddChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView = (TextView) findViewById(R.id.empty_view);

        fabAddChar = (FloatingActionButton) findViewById(R.id.add_char);

        ListView starWarsCharsListView = (ListView) findViewById(R.id.list);
        SQLUtils db = new SQLUtils(getApplicationContext());
        charArrayList = db.getAllStarWarsChars();
        adapter = new StarWarsCharListAdapter(this, charArrayList );

        starWarsCharsListView.setAdapter(adapter);
        starWarsCharsListView.setEmptyView(emptyStateTextView);
        emptyStateTextView.setText(R.string.no_chars_registered);

        ArrayList<String> validURLs = db.getAllStarWarsValidURL();

        if(validURLs.size() == 0) {
            if (DeviceHelper.checkInternetConnection(getApplicationContext())) {
                starWarsValidURLsTask = new StarWarsValidURLsAsyncTask();
                starWarsValidURLsTask.execute();
            }
        }
        db.close();
    }

    public void launchScannerActivity(View v) {
        fabAddChar.setEnabled(false);
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            if (DeviceHelper.checkInternetConnection(getApplicationContext())) {
                SQLUtils db = new SQLUtils(getApplicationContext());
                if (db.getAllStarWarsValidURL().size() > 0) {
                    launchActivity(ScannerActivity.class);
                } else {
                    starWarsValidURLsTask = new StarWarsValidURLsAsyncTask();
                    starWarsValidURLsTask.execute();
                    launchActivity(ScannerActivity.class);
                }

            } else {
                fabAddChar.setEnabled(true);
                Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
            }
        }else{
            fabAddChar.setEnabled(true);
            Toast.makeText(this, getString(R.string.no_camera_found), Toast.LENGTH_SHORT).show();
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
        fabAddChar.setEnabled(true);
        if(requestCode == QRCODE_INTENT){
            if(resultCode == RESULT_OK){
                if(DeviceHelper.checkInternetConnection(getApplicationContext())) {
                    loadingIndicator.setVisibility(View.VISIBLE);
                    emptyStateTextView.setVisibility(View.GONE);

                    apiCharURL = data.getStringExtra("url");
                    starWarsCharsTask = new StarWarsCharsAsyncTask();
                    starWarsCharsTask.execute();
                }else{
                    Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show();
                }

            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, R.string.action_cancelled, Toast.LENGTH_SHORT).show();
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
                    fabAddChar.setEnabled(true);
                }
        }
    }

    //region Star Wars Chars AsyncTask
    private class StarWarsCharsAsyncTask extends AsyncTask<Void, Void, StarWarsChar> {

        @Override
        protected StarWarsChar doInBackground(Void... urls) {
            if(apiCharURL == null){
                return null;
            }

            return ApiUtils.fetchCharData(apiCharURL);
        }

        @Override
        protected void onPostExecute(StarWarsChar starWarsChar) {
            starWarsCharsTask = null;
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
                int index = 0;
                for(int i = 0; i < charArrayList.size();i++){
                    if(charArrayList.get(i).getName().equals(starWarsChar.getName())){
                        index = i;
                        i = charArrayList.size() + 1;
                    }
                }
                db.updateStarWarsChar(starWarsChar);
                charArrayList.set(index,starWarsChar);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.chars_updated, Toast.LENGTH_SHORT).show();
                loadingIndicator.setVisibility(View.GONE);
            }

        }
    }
    //endregion

    //region Star Wars Valid URLs AsyncTask
    private class StarWarsValidURLsAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... urls) {

            return ApiUtils.fetchValidURLData(API_VALID_URL);
        }

        @Override
        protected void onPostExecute(ArrayList<String> validURLs) {

            starWarsValidURLsTask = null;
            SQLUtils db = new SQLUtils(getApplicationContext());
            if( validURLs != null)
                if (validURLs.size() > 0) {
                   db.insertStarWarsValidURLs(validURLs);
                }
        }
    }
    //endregion
}
