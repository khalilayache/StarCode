package com.khalilayache.starcode.views;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.adapters.StarWarsFragmentAdapter;
import com.khalilayache.starcode.models.StarWarsChar;

import java.util.ArrayList;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public class DetailsActivity extends AppCompatActivity {

    private  ArrayList<String> TAB_TITLES = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TAB_TITLES.add(getString(R.string.pager_title_info));
        TAB_TITLES.add(getString(R.string.pager_title_films));
        TAB_TITLES.add(getString(R.string.pager_title_ships));
        TAB_TITLES.add(getString(R.string.pager_title_vehicle));

        Intent intent = this.getIntent();
        StarWarsChar starWarsChar = (StarWarsChar) intent.getSerializableExtra("char");

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(starWarsChar.getName());
        toolbar.setElevation(0f);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new StarWarsFragmentAdapter(getSupportFragmentManager(),starWarsChar, TAB_TITLES));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }
}
