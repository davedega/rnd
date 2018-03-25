package com.dega.backbase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDActivity extends AppCompatActivity {

    RnDPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper);

        attachListFragment();

        presenter.loadEntries();
    }


    private void attachListFragment() {
        RnDFragment entriesFragment = (RnDFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);
        if (entriesFragment == null) {
            entriesFragment = RnDFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, entriesFragment).commit();
            presenter = new RnDPresenter(this, entriesFragment);
            entriesFragment.setPresenter(presenter);
            presenter.loadEntries();

        }
    }


    public void aja(){
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment =  SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.content_frame, supportMapFragment).commit();
    }
}
