package com.dega.backbase;

import android.content.Context;
import android.os.AsyncTask;

import com.dega.backbase.model.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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


    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public RnDPresenter() {
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
    public void showEntries() {
        view.showEntriesInList(context, entries);
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
                InputStream inputStream = context.getAssets().open("cities.json");
                Gson gson = new GsonBuilder().create();
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                List<Entry> entries = new ArrayList<>();
                reader.beginArray();

                while (reader.hasNext()) {
                    Entry message = gson.fromJson(reader, Entry.class);
                    entries.add(message);
                    //todo publishProgress
                }
                reader.endArray();
                reader.close();
                setEntries(entries);
                return entries;

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
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
}
