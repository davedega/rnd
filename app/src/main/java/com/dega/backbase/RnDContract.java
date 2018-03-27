package com.dega.backbase;

import android.content.Context;

import com.dega.backbase.model.Entry;

import java.util.List;

/**
 * Created by davedega on 25/03/18.
 */
public interface RnDContract {

    interface Presenter {

        void start();

        void showEntries();

        void showDetailInNewView(Entry entry);
    }

    interface View {

        void setPresenter(Presenter presenter);

        void showEntriesInList(Context context, List<Entry> loadedEntries);

        void showErrorMessage(int message);

    }
}
