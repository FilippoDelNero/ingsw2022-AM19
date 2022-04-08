package it.polimi.ingsw.am19.Model.Utilities;
import it.polimi.ingsw.am19.Model.BoardManagement.Bag;
import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Utilities.IslandList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * testing class for IslandList
 */
public class IslandListTest {
    @BeforeEach
    void removeAllFromBag(){
        Bag bag = Bag.getBagInstance();
        try {
            bag.removeAll();
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }
    /**
     * testing the first alternative constructor
     * it takes a list and return an IslandList with the same elements
     */
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

    /**
     * testing the second alternative constructor
     * it takes a int as a parameter and build an IslandList of that length
     */
    @Test
    public void testSizeConstructor() {
        //I am just testing the the constructor is broken, but i can't test that the list created is 5 long
        IslandList<Integer> list = new IslandList<>(5);
        for(int i = 0; i < 5; i++) {
            list.add(i);
        }
        assertEquals(5, list.size());
    }

    /**
     * testing the next() method of the iterator in normal condition
     */
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

    /**
     * testing the next() method of the iterator on an empty list
     */
    @Test
    public void testNextExceptional() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        assertThrows(NoSuchElementException.class, itr::next);
    }

    /**
     * testing the nextIndex() method of the iterator on an empty list
     */
    @Test
    public void testNextIndexExceptional() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        assertThrows(NoSuchElementException.class, itr::nextIndex);
    }

    /**
     * testing the previous() method of the iterator in normal condition
     */
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

    /**
     * testing the previous() method of the iterator on an empty list
     */
    @Test
    public void testPreviousExceptional() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        assertThrows(NoSuchElementException.class, itr::previous);
    }

    /**
     * testing the previousIndex() method of the iterator on an empty list
     */
    @Test
    public void testPreviousIndexExceptional() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        assertThrows(NoSuchElementException.class, itr::previousIndex);
    }

    /**
     * testing the remove() method of the iterator in normal condition
     */
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

    /**
     * testing the remove() method of the iterator on an empty list
     */
    @Test
    public void testExceptionalRemove() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        assertThrows(NoSuchElementException.class, itr::remove);
    }

    /**
     * testing the add() method of the iterator on an empty list
     */
    @Test
    public void testAdd() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        itr.add(1);
        assertEquals(1, list.size());
        assertEquals(1, itr.next());
    }

    /**
     * testing the add() method of the iterator in normal condition
     */
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

    /**
     * testing the set() method of the iterator in normal condition
     */
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

    /**
     * testing the set() method of the iterator on an empty list
     */
    @Test
    public void testSetExceptional() {
        IslandList<Integer> list = new IslandList<>();
        ListIterator<Integer> itr = list.iterator();
        Integer newInteger = 1;
        assertThrows(NoSuchElementException.class, () -> itr.set(newInteger));
    }
}
