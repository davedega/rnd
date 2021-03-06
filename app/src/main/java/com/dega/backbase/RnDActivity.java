package com.dega.backbase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.dega.backbase.model.Entry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDActivity extends AppCompatActivity implements OnMapReadyCallback, MapContract.Presenter {

    private RnDPresenter presenter;
    private GoogleMap mMap;

    //to center the map en the city selected
    private LatLng citySelected;
    private Entry entrySelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper);
        attachListFragment();
    }

    private void attachListFragment() {
        RnDFragment entriesFragment = (RnDFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (entriesFragment == null) {
            entriesFragment = RnDFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, entriesFragment).commit();
            presenter = new RnDPresenter(this, entriesFragment);
            entriesFragment.setPresenter(presenter);
            presenter.start();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(citySelected).title(entrySelected.toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(citySelected, 10));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showMap(Entry entry) {
        this.entrySelected = entry;
        citySelected = new LatLng(entrySelected.getCoord().getLat(), entrySelected.getCoord().getLon());
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().add(R.id.content_frame, supportMapFragment).addToBackStack(null).commit();
        supportMapFragment.getMapAsync(this);
    }
}
