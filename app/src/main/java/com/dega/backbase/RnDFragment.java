package com.dega.backbase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dega.backbase.model.Entry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDFragment extends Fragment implements RnDContract.View {

    private RnDContract.Presenter presenter;

    private ListView listView;
    private ProgressBar progressBar;
    private LinearLayout loadingContainer;

    private EditText searchEditText;

    private EntriesAdapter adapter;
    private List<Entry> originalentries;
    private List<Entry> entries;

    public static RnDFragment newInstance() {
        RnDFragment fragment = new RnDFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.entries_fragment, container, false);
        listView = rootView.findViewById(R.id.entriesListView);
        progressBar = rootView.findViewById(R.id.progressBar);
        loadingContainer = rootView.findViewById(R.id.loadingContainer);
        searchEditText = rootView.findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable workRunnable;

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    final Set<Entry> entriesSet = new HashSet<Entry>(originalentries);

                    handler.removeCallbacks(workRunnable);
                    workRunnable = new Runnable() {
                        @Override
                        public void run() {
                            Set<Entry> prefixEntries = prefixName(entriesSet, s.toString());
                            entries.clear();
                            entries.addAll(prefixEntries);
                            adapter.notifyDataSetChanged();
                        }
                    };
                    handler.postDelayed(workRunnable, 300 /*delay*/);
                } else {
                    // Reset the adapter to orignal state
                    entries.clear();
                    entries.addAll(originalentries);
                    adapter.notifyDataSetChanged();
                }

            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(RnDContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showEntriesInList(Context context, List<Entry> loadedEntries) {
        this.originalentries = new ArrayList<>();
        this.originalentries.addAll(loadedEntries);
        this.entries = loadedEntries;
        progressBar.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter = new EntriesAdapter(context, this.entries);
        listView.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    // todo explanation of this
    public Set<Entry> prefixName(final Set<Entry> entries, final String prefix) {
        final Set<Entry> entriesWithPrefix = new HashSet<>();
        for (final Entry entry : entries) {
            if (entry.getName().startsWith(prefix)) {
                entriesWithPrefix.add(entry);
            }
        }
        return entriesWithPrefix;
    }

    // The Adapter lives within the view since is the only class who access it
    class EntriesAdapter extends ArrayAdapter<Entry> {

        Context context;
        List<Entry> entries;

        EntriesAdapter(@NonNull Context context, @NonNull List<Entry> objects) {
            super(context, -1, objects);
            this.context = context;
            this.entries = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.entry_list_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.entryName);
            textView.setText(entries.get(position).toString());
            return rowView;
        }
    }
}
