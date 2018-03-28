package com.dega.backbase;

import com.dega.backbase.model.Entry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test requirement: show cities on a scrollable list in alphabetic order (city first, country after)
 * "Denver, US" should appear before, "Sydney, Australia"
 * "Anaheim, US" should appear before "Denver, US"
 * Created by davedega on 27/03/18.
 */
public class SortTest {

    RnDPresenter presenter = new RnDPresenter();
    List<Entry> entries = new ArrayList<>();
    List<Entry> expected = new ArrayList<>();


    @Before
    public void setUp() {

        presenter = new RnDPresenter();

        entries.add(new Entry("ZH", "Denver"));
        entries.add(new Entry("FG", "Denver"));
        entries.add(new Entry("US", "Alabama"));
        entries.add(new Entry("RT", "Alabama"));
        entries.add(new Entry("AU", "Sydney"));
        entries.add(new Entry("US", "Albuquerque"));
        entries.add(new Entry("US", "Arizona"));
        entries.add(new Entry("US", "Anaheim"));

        expected.add(new Entry("RT", "Alabama"));
        expected.add(new Entry("US", "Alabama"));
        expected.add(new Entry("US", "Albuquerque"));
        expected.add(new Entry("US", "Anaheim"));
        expected.add(new Entry("US", "Arizona"));
        expected.add(new Entry("FG", "Denver"));
        expected.add(new Entry("ZH", "Denver"));
        expected.add(new Entry("AU", "Sydney"));

    }

    @Test
    public void sortAlphabetical() throws Exception {

        List<Entry> sorted = presenter.sortAlphabetical(entries);

        assertArrayEquals(sorted.toArray(), expected.toArray());

        assertTrue(expected.equals(sorted));

    }

    @Test
    public void sortAlphabeticalFail() throws Exception {
        
        assertFalse(expected.equals(entries));

    }


}