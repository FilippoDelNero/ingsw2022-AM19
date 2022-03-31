package it.polimi.ingsw.am19.Model;

import static it.polimi.ingsw.am19.Model.Bag.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testing class for Bag class methods
 */
public class BagTest {
    /**
     * Tests bag refill with an exceeding number of students of a specific color
     */
    @Test
    void refillWithExceedingNumStudentsPerColor() {
        Bag bag = getBagInstance();
        assertThrows(ExceedingStudentsPerColorException.class,
                () -> bag.refillWith(PieceColor.RED, 27));
    }

    /**
     * Tests bag refill on a completely full bag (maximum number of students plus one)
     */
    @Test
    void refillFullBag(){
        Bag bag = getBagInstance();
        for (PieceColor color: PieceColor.values()) {
            try {
                bag.refillWith(color,26);
            } catch (ExceedingStudentsPerColorException e) {
                e.printStackTrace();
            }
        }
        assertThrows(ExceedingStudentsPerColorException.class,
                () -> bag.refillWith(PieceColor.YELLOW,1));
    }

    /**
     * Tests bag refill with a negative number of students of a specified color
     */
    @Test
    void refillWithNegNumOfStudents(){
        Bag bag = getBagInstance();
        assertThrows(IllegalArgumentException.class,
                () -> bag.refillWith(PieceColor.BLUE,-1));
    }

    /**
     * Tests bag refill with a null PieceColor
     */
    @Test
    void refillWithNullColor(){
        Bag bag = getBagInstance();
        assertThrows(IllegalArgumentException.class,
                () -> bag.refillWith(null,5));
    }

    /**
     * Tests if drawing from an empty bag is truly not permitted
     */
    @Test
    void drawFromEmptyBag(){
        Bag bag = getBagInstance();
        assertThrows(EmptyBagException.class,
                () -> bag.drawStudent());
    }

    /**
     * Tests multiple drawings from the bag until it's empty
     */
    @Test
    void drawMultipleTimes() {
        Bag bag = getBagInstance();
        try {
            bag.refillWith(PieceColor.RED, 0);
            bag.refillWith(PieceColor.YELLOW, 26);
            bag.refillWith(PieceColor.PINK, 2);
            bag.refillWith(PieceColor.BLUE, 15);
            bag.refillWith(PieceColor.GREEN, 10);
        } catch (ExceedingStudentsPerColorException e) {
            e.printStackTrace();
        }
        int tot =  0 + 26 + 2 + 15 + 10;
        for (int iter = 0; iter < tot; iter++) {
            try {
                System.out.println(bag.drawStudent());
            } catch (EmptyBagException e) {
                e.printStackTrace();
            }
        }
        assertThrows(EmptyBagException.class,
                () -> System.out.println(bag.drawStudent()));
        }

    /**
     * Tests if trying instantiating multiple Bag instances produces just one Bag instance
     */
    @Test
    void multipleBagInstances(){
        Bag b1 = Bag.getBagInstance();
        Bag b2 = Bag.getBagInstance();
        assertTrue(b1 == b2);
    }
}
