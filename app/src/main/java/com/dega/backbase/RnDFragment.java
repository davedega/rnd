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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

    private LinearLayout searchContainer;
    private LinearLayout loadingContainer;
    private ListView listView;
    private ProgressBar progressBar;

    private EditText searchEditText;

    private EntriesAdapter adapter;
    private List<Entry> originalentries;
    private List<Entry> entries;

    View rootView;

    public static RnDFragment newInstance() {
        RnDFragment fragment = new RnDFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.entries_fragment, container, false);
        listView = rootView.findViewById(R.id.entriesListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Entry entry = (Entry) listView.getItemAtPosition(position);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                presenter.showDetailInNewView(entry);

            }
        });
        progressBar = rootView.findViewById(R.id.progressBar);
        searchContainer = rootView.findViewById(R.id.searchContainer);
        loadingContainer = rootView.findViewById(R.id.loadingContainer);

        rootView.findViewById(R.id.clearImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEditText.setText("");
            }
        });

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

                    // HashSet is much faster than TreeSet but offers no ordering guarantees like TreeSet.
                    // a HashSet is only fast if you know the size of your dataset in advance, and this is the case here.
                    final Set<Entry> entriesSet = new HashSet<>(originalentries);

                    handler.removeCallbacks(workRunnable);
                    workRunnable = new Runnable() {
                        @Override
                        public void run() {

                            Set<Entry> prefixEntries = presenter.searchByPrefix(entriesSet, s.toString());
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setPresenter(RnDContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showEntriesInList(Context context, List<Entry> loadedEntries) {
        showSearchCity();
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

    @Override
    public void showSearchCity() {
        searchContainer.setVisibility(View.VISIBLE);
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
