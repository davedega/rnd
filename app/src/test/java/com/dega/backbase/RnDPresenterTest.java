package com.dega.backbase;

import com.dega.backbase.model.Entry;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by davedega on 27/03/18.
 */
public class RnDPresenterTest {

    RnDPresenter presenter = new RnDPresenter();
    HashSet<Entry> entries = new HashSet<>();
    HashSet<Entry> expected = new HashSet<>();
    Set<Entry> result;


    @Before
    public void setUp() {

        entries.add(new Entry("US", "Alabama"));
        entries.add(new Entry("US", "Albuquerque"));
        entries.add(new Entry("US", "Anaheim"));
        entries.add(new Entry("US", "Arizona"));
        entries.add(new Entry("AU", "Sydney"));

        expected.add(new Entry("US", "Alabama"));
        expected.add(new Entry("US", "Albuquerque"));
        expected.add(new Entry("US", "Anaheim"));
        expected.add(new Entry("US", "Arizona"));
    }


    @Test
    public void searchByPrefixA() throws Exception {

        // Given prefix is 'A', all cities but Sydney should appear.
        result = presenter.searchByPrefix(entries, "a");

        assertTrue(result != null);

        assertTrue(result.size() == 4);

        assertTrue(expected.equals(result));
    }


    @Test
    public void searchByPrefixS() throws Exception {

        // Given prefix is “s”, the only result should be “Sydney, AU”.
        result = presenter.searchByPrefix(entries, "s");

        assertTrue(result != null);

        assertTrue(result.size() == 1);

        assertEquals(result.iterator().next().getName(), "Sydney");
    }


    @Test
    public void searchByPrefixInvalid() throws Exception {

        // Given invalid prefix result should be empty.

        result = presenter.searchByPrefix(entries, "=");

        assertTrue(result.size() == 0);
    }

    @Test
    public void searchByPrefixNull() throws Exception {

        // Given invalid prefix result should be empty.

        result = presenter.searchByPrefix(entries, null);

        assertTrue(result.size() == 0);

        // Given invalid prefix result should be empty.

        result = presenter.searchByPrefix(null, null);

        assertTrue(result.size() == 0);

    }

}