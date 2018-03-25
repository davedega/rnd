package com.dega.backbase;

import com.dega.backbase.model.EntriesResponse;
import com.dega.backbase.model.Entry;

/**
 * Created by davedega on 25/03/18.
 */
public interface RnDContract {

    interface Presenter {

        Entry[] loadEntries();

        void showDetailInNewView(Entry entry);
    }

    interface View {

        void setPresenter(Presenter presenter);

        void showEntriesInList(EntriesResponse entriesResponse);

        void showErrorMessage(int message);

        void showLastUpdate();
    }
}
