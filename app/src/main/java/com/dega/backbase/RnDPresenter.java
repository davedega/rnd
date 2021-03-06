package com.dega.backbase;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.dega.backbase.model.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDPresenter implements RnDContract.Presenter {

    private Context context;
    private RnDContract.View view;
    private List<Entry> entries;

    Handler handler = new Handler();

    void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    RnDPresenter() {
    }

    RnDPresenter(Context context, RnDContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void start() {
        EntriesTask myTask = new EntriesTask();
        myTask.execute();
    }

    @Override
    public void showDetailInNewView(Entry entry) {
        ((RnDActivity) context).showMap(entry);
    }

    @Override
    public Set<Entry> searchByPrefix(Set<Entry> entries, String prefix) {
        final Set<Entry> entriesWithPrefix = new HashSet<>();
        if (entries == null || prefix == null) {
            return entriesWithPrefix;
        }
        for (final Entry entry : entries) {
            if (entry.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                entriesWithPrefix.add(entry);
            }
        }
        return entriesWithPrefix;
    }


    // the purpose of this class is to load the file in background
    class EntriesTask extends AsyncTask<Void, Integer, List<Entry>> {
        @Override
        protected List<Entry> doInBackground(Void... voids) {
            try {
                Gson gson = new GsonBuilder().create();
                List<Entry> entries = new ArrayList<>();
                List<Entry> sorted;
                InputStream inputStream = context.getAssets().open("cities.json");
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

                reader.beginArray();

                while (reader.hasNext()) {
                    Entry message = gson.fromJson(reader, Entry.class);
                    entries.add(message);
                }

                // Inform the user when cities are loaded
                publishProgress(R.string.cities_loaded);
                reader.endArray();
                reader.close();
                setEntries(entries);


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Inform the user
                        publishProgress(R.string.starting_app);

                    }
                }, 1500);

                sorted = sortAlphabetical(entries);

                return sorted;

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            view.informUser(values[0]);
        }

        @Override
        protected void onPostExecute(List<Entry> entries) {
            super.onPostExecute(entries);
            if (entries != null) {
                view.showEntriesInList(context, entries);
            } else {
                view.showErrorMessage(R.string.entries_not_loaded);
            }
        }
    }


    public List<Entry> sortAlphabetical(List<Entry> entries) {
        Collections.sort(entries, new Comparator<Entry>() {
            @Override
            public int compare(Entry entry1, Entry entry2) {
                String s1 = entry1.getName();
                String s2 = entry2.getName();
                int i = s1.compareToIgnoreCase(s2);
                if (i != 0) return i;

                String c1 = entry1.getCountry();
                String c2 = entry2.getCountry();
                i = c1.compareToIgnoreCase(c2);

                return i;

            }
        });

        return entries;
    }
}
