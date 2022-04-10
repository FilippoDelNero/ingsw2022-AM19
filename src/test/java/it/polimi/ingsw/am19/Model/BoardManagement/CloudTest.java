package it.polimi.ingsw.am19.Model.BoardManagement;

import it.polimi.ingsw.am19.Model.Exceptions.EmptyBagException;
import it.polimi.ingsw.am19.Model.Exceptions.NoSuchColorException;
import it.polimi.ingsw.am19.Model.Exceptions.TooManyStudentsException;
import it.polimi.ingsw.am19.Model.Utilities.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
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
     * Tests removing students of all different colors from a cloud which has actually no students on it
     */
    @Test
    public void removeWhenEmpty(){
        Cloud c = new Cloud(3);

        assertThrows(NoSuchColorException.class,() -> c.removeStudent(PieceColor.RED));
    }

    /**
     * Tests removing a student of a wrong color
     */
    @Test
    void removeWrongColor(){
        //instantiating an empty cloud
        Cloud c = new Cloud(3);

        //adding a red student
        assertDoesNotThrow(() -> c.addStudent(PieceColor.RED));

        //trying to remove a yellow student
        assertThrows(NoSuchColorException.class,
                () -> c.removeStudent(PieceColor.YELLOW));
    }

    /**
     * Tests if students are removed correctly from the cloud
     */
    @Test
    void removeCorrectly(){
        Cloud c = new Cloud(4);

        assertDoesNotThrow(() -> c.addStudent(PieceColor.GREEN));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.BLUE));

        assertDoesNotThrow(() -> c.removeStudent(PieceColor.GREEN));
        assertEquals(0, c.getStudents().get(PieceColor.GREEN));

        assertDoesNotThrow(() -> c.removeStudent(PieceColor.BLUE));
        assertEquals(1, c.getStudents().get(PieceColor.BLUE));

        assertDoesNotThrow(() -> c.removeStudent(PieceColor.BLUE));
        assertEquals(0, c.getStudents().get(PieceColor.BLUE));
    }

    /**
     * Tests adding a student on a full cloud
     */
    @Test
    void addOnFullCloud() {
        Cloud c = new Cloud(3);

        //adding as many students as the cloud's capacity
        assertDoesNotThrow(() -> c.addStudent(PieceColor.BLUE));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.YELLOW));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.PINK));

        //adding an extra student
        assertThrows(TooManyStudentsException.class,
                () -> c.addStudent(PieceColor.PINK));
    }

    /**
     * Tests the correctness of the Cloud's getStudents() method
     */
    @Test
    void getStudentsCorrectly(){
        Cloud c = new Cloud(3);

        assertDoesNotThrow(() -> c.addStudent(PieceColor.PINK));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.PINK));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.YELLOW));

        assertEquals(2, c.getStudents().get(PieceColor.PINK));
        assertEquals(1, c.getStudents().get(PieceColor.YELLOW));
        assertEquals(0, c.getStudents().get(PieceColor.BLUE));
        assertEquals(0, c.getStudents().get(PieceColor.GREEN));
        assertEquals(0, c.getStudents().get(PieceColor.RED));
    }

    /**
     * Tests if removing a student from the Map returned by the Cloud's getStudents() method is an actual copy of the private attribute
     */
    @Test
    void isMapCopy(){
        Cloud c = new Cloud(3);

        assertDoesNotThrow(() -> c.addStudent(PieceColor.PINK));
        assertDoesNotThrow(() -> c.addStudent(PieceColor.PINK));

        assertEquals(2, c.getStudents().get(PieceColor.PINK));

        Map<PieceColor,Integer> mapCopy;
        mapCopy = c.getStudents();

        assertDoesNotThrow(() -> c.removeStudent(PieceColor.PINK));

        assertNotSame(c.getStudents().get(PieceColor.PINK), mapCopy.get(PieceColor.PINK));
    }

    /**
     * Tests the correctness of the Cloud's getNumOfHostableStudents() method
     */
    @Test
    void getCorrectHostableStudents(){
        Cloud c = new Cloud(3);

        assertEquals(3, c.getNumOfHostableStudents());
    }

    /**
     * Tests the correctness of the getCurrNumStudents() method
     */
    @Test
    void getCorrectCurrNumStudents(){
        Cloud c = new Cloud(3);
        assertEquals(0, c.getCurrNumOfStudents());

        assertDoesNotThrow(() -> c.addStudent(PieceColor.GREEN));
        assertEquals(1, c.getCurrNumOfStudents());

        assertDoesNotThrow(() -> c.addStudent(PieceColor.BLUE));
        assertEquals(2, c.getCurrNumOfStudents());

        assertDoesNotThrow(() -> c.removeStudent(PieceColor.BLUE));
        assertEquals(1,c.getCurrNumOfStudents());
    }
}
