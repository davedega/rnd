package com.dega.backbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dega.backbase.model.Entry;

import java.util.List;

/**
 * Created by davedega on 25/03/18.
 */
public class RnDFragment extends Fragment implements RnDContract.View {

    private ListView listView;
    private ProgressBar progressBar;
    private LinearLayout loadingContainer;
    private RnDContract.Presenter presenter;


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
    public void showEntriesInList(Context context, List<Entry> entries) {
        progressBar.setVisibility(View.GONE);
        loadingContainer.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        EntriesAdapter adapter = new EntriesAdapter(context, entries);
        listView.setAdapter(adapter);
    }

    @Override
    public void showErrorMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
