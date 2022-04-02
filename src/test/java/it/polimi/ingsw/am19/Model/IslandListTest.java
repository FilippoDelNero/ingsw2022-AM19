package it.polimi.ingsw.am19.Model;

import org.junit.jupiter.api.Test;

import java.util.ListIterator;

public class IslandListTest {

    @Test
    public void testNext() {
        IslandList<Integer> list = new IslandList<>();
        for(Integer i = 0; i < 10; i++) {
            list.add(i);
        }
        ListIterator<Integer> itr = list.iterator();
        for(int j = 0; j < 20; j++) {
            Integer currentValue = itr.next();
            System.out.println(currentValue);
        }
    }
}
