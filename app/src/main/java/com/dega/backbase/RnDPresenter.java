package com.dega.backbase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dega.backbase.model.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDPresenter implements RnDContract.Presenter {

    private final Context context;

    private final String FILENAME = "/cities.json";

    private RnDContract.View view;

    public RnDPresenter(Context context, RnDContract.View view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public Entry[] loadEntries() {

        try {
            String json = null;
            InputStream inputStream = context.getAssets().open("cities.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            Gson gson = new GsonBuilder().create();
            Entry[] entries = gson.fromJson(json, Entry[].class);
            return entries;

        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("Presenter", "" + ex.getMessage());
            return null;
        }
    }

    @Override
    public void showDetailInNewView(Entry entry) {

        ((RnDActivity)context).aja();

//        Bundle extras = new Bundle();
//        extras.putInt(Constants.Data.VEHICLE_ID, entry.getVehicleId());
//        extras.putString(Constants.Data.VRN, entry.getVrn());
//        extras.putString(Constants.Data.COUNTRY, entry.getCountry());
//        extras.putString(Constants.Data.COLOR, entry.getColor());
//        extras.putString(Constants.Data.TYPE, entry.getType());
//        extras.putBoolean(Constants.Data.DEFAULT, entry.getDefault());
//
//        Intent detail = new Intent(context, ParkDetalActivity.class);
//        detail.putExtras(extras);
//        context.startActivity(detail);
    }
}
