package com.dega.backbase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RnDPresenter presenter;
    private GoogleMap mMap;

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


    public void aja(){
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment =  SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.content_frame, supportMapFragment).commit();
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
