package com.khalilayache.starcode.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.views.fragments.CharInfoFragment;
import com.khalilayache.starcode.views.fragments.FilmsFragment;
import com.khalilayache.starcode.views.fragments.ShipsFragment;
import com.khalilayache.starcode.views.fragments.VehicleFragment;

import java.util.ArrayList;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */
public class StarWarsFragmentAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 4;
    private StarWarsChar starWarsChar;
    private ArrayList<String> tabTitles;

    public StarWarsFragmentAdapter(FragmentManager fm, StarWarsChar starWarsChar,ArrayList<String> tabTitles) {
        super(fm);
        this.starWarsChar = starWarsChar;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putSerializable("char",starWarsChar);
                CharInfoFragment CharInfoFragment = new CharInfoFragment();
                CharInfoFragment.setArguments(bundle);
                return CharInfoFragment;
            case 1:
                bundle.putSerializable("char",starWarsChar);
                FilmsFragment FilmsFragment = new FilmsFragment();
                FilmsFragment.setArguments(bundle);
                return FilmsFragment;
            case 2:
                bundle.putSerializable("char",starWarsChar);
                ShipsFragment ShipsFragment = new ShipsFragment();
                ShipsFragment.setArguments(bundle);
                return ShipsFragment;
            case 3:
                bundle.putSerializable("char",starWarsChar);
                VehicleFragment VehicleFragment = new VehicleFragment();
                VehicleFragment.setArguments(bundle);
                return VehicleFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
