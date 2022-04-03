package it.polimi.ingsw.am19.Model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IslandListTest {
    @Test
    public void testAltConstructor() {
        List<Integer> ls = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            ls.add(i);
        }

        IslandList<Integer> list = new IslandList<>(ls);
        List<Integer> copy = list.copy();

        for (int i = 0; i < 10; i++) {
            assertEquals(ls.get(i), copy.get(i));
        }
    }

    @Test
    public void testNext() {
        IslandList<Integer> list = new IslandList<>();

        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        ListIterator<Integer> itr = list.iterator();
        int i = 0;

        for(int j = 0; j < 20; j++) {
            Integer currentValue = itr.next();
            assertEquals(i, currentValue);
            i++;
            if(i == 10) {
                i = 0;
            }
        }
    }

    @Test
    public void testPrevious() {
        IslandList<Integer> list = new IslandList<>();

        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        ListIterator<Integer> itr = list.iterator();
        int i = 9;

        for(int j = 0; j < 20; j++) {
            Integer currentValue = itr.previous();
            assertEquals(i, currentValue);
            i--;
            if(i < 0) {
                i = 9;
            }
        }
    }

    @Test
    public void testNormalRemove() {
        IslandList<Integer> list = new IslandList<>();

        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        ListIterator<Integer> itr = list.iterator();

        for(int i = 0; i < 5; i++) {
            itr.next();
        }
        itr.remove();
        assertEquals(9, list.size());

        for(int i = 3; i > 0; i--) {
            Integer currentValue = itr.previous();
            assertEquals(i-1, currentValue);
        }

        for(int i = 1; i < 10; i++) {
            Integer currentValue = itr.next();
            if(i == 4)
                i++;
            assertEquals(i, currentValue);
        }
    }

    @Test
    public void testExceptionalRemove() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        assertThrows(NoSuchElementException.class, itr::remove);
    }

    @Test
    public void testAdd() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        itr.add(1);
        assertEquals(1, list.size());
        assertEquals(1, itr.next());
    }

    @Test
    public void testAddToNonEmptyList() {
        IslandList<Integer> list = new IslandList<>();
        list.add(0);
        assertEquals(1, list.size());
        ListIterator<Integer> itr = list.iterator();
        itr.add(1);
        assertEquals(2, list.size());
        assertEquals(1, itr.next());
        assertEquals(0, itr.next());
    }

    @Test
    public void testSet() {
        IslandList<Integer> list = new IslandList<>();
        list.add(0);
        assertEquals(1, list.size());
        ListIterator<Integer> itr = list.iterator();
        assertEquals(0, itr.next());
        itr.set(1);
        assertEquals(1, list.size());
        assertEquals(1, itr.next());
    }
}
