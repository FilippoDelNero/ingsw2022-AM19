package it.polimi.ingsw.am19.Model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {
    /**
     * Tests removing students of all different colors from a cloud which has actually no students on it
     */
    @Test
    public void removeWhenEmpty(){
        Cloud c = new Cloud(3);

        assertThrows(NoSuchColorException.class,() -> c.removeStudent(PieceColor.RED));
        assertThrows(NoSuchColorException.class,() -> c.removeStudent(PieceColor.YELLOW));
        assertThrows(NoSuchColorException.class,() -> c.removeStudent(PieceColor.BLUE));
        assertThrows(NoSuchColorException.class,() -> c.removeStudent(PieceColor.GREEN));
        assertThrows(NoSuchColorException.class,() -> c.removeStudent(PieceColor.RED));
    }

    /**
     * Tests removing a student of a wrong color
     */
    @Test
    void removeWrongColor(){
        Cloud c = new Cloud(3);
        try {
            c.addStudent(PieceColor.RED);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        assertThrows(NoSuchColorException.class,
                () -> c.removeStudent(PieceColor.YELLOW));
    }

    /**
     * Tests if students are removed correctly from the cloud
     */
    @Test
    void removeCorrectly(){
        Cloud c = new Cloud(4);

        try {
            c.addStudent(PieceColor.GREEN);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.BLUE);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.BLUE);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }

        try {
            c.removeStudent(PieceColor.GREEN);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        assertEquals(0, c.getStudents().get(PieceColor.GREEN));

        try {
            c.removeStudent(PieceColor.BLUE);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        assertEquals(1, c.getStudents().get(PieceColor.BLUE));

        try {
            c.removeStudent(PieceColor.BLUE);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        assertEquals(0, c.getStudents().get(PieceColor.BLUE));
    }

    /**
     * Tests removing a null PieceColor
     */
    @Test
    void removeNullColor(){
        Cloud c = new Cloud(4);
        assertThrows(IllegalArgumentException.class,
                () -> c.removeStudent(null));
    }

    /**
     * Tests adding a null PieceColor
     */
    @Test
    void addNullColor(){
        Cloud c = new Cloud(4);
        assertThrows(IllegalArgumentException.class,
                () -> c.addStudent(null));
    }

    /**
     * Tests adding a student on a full cloud
     */
    @Test
    void addOnFullCloud() {
        Cloud c = new Cloud(3);

        try {
            c.addStudent(PieceColor.BLUE);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.YELLOW);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.PINK);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        assertThrows(TooManyStudentsException.class,
                () -> c.addStudent(PieceColor.PINK));
    }

    /**
     * Tests the correctness of the Cloud's getStudents() method
     */
    @Test
    void getStudentsCorrectly(){
        Cloud c = new Cloud(3);

        try {
            c.addStudent(PieceColor.PINK);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.PINK);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.YELLOW);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }

        assertEquals(2, c.getStudents().get(PieceColor.PINK));
        assertEquals(1, c.getStudents().get(PieceColor.YELLOW));
        assertEquals(0, c.getStudents().get(PieceColor.BLUE));
        assertEquals(0, c.getStudents().get(PieceColor.GREEN));
        assertEquals(0, c.getStudents().get(PieceColor.RED));
    }

    /**
     * Tests if removing a student from the Map returned by the CLoud's getStudents() method is an actual copy of the private attribute
     */
    @Test
    void isMapCopy(){
        Cloud c = new Cloud(3);

        try {
            c.addStudent(PieceColor.PINK);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        try {
            c.addStudent(PieceColor.PINK);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }

        assertEquals(2, c.getStudents().get(PieceColor.PINK));

        Map<PieceColor,Integer> mapCopy = new HashMap<>();
        mapCopy = c.getStudents();

        try {
            c.removeStudent(PieceColor.PINK);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }

        assertEquals(true, c.getStudents().get(PieceColor.PINK) != mapCopy.get(PieceColor.PINK));
    }

    /**
     * Tests the correctness of the Cloud's getNumOfHostableStudents() method
     */
    @Test
    void getCorrectHostableStudents(){
        Cloud c = new Cloud(3);

        assertEquals(3, c.getNumOfHostableStudents());
    }

    @Test
    void getCorrectCurrNumStudents(){
        Cloud c = new Cloud(3);
        assertEquals(0, c.getCurrNumOfStudents());

        try {
            c.addStudent(PieceColor.GREEN);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }

        assertEquals(1, c.getCurrNumOfStudents());

        try {
            c.addStudent(PieceColor.BLUE);
        } catch (TooManyStudentsException e) {
            e.printStackTrace();
        }
        assertEquals(2, c.getCurrNumOfStudents());

        try {
            c.removeStudent(PieceColor.BLUE);
        } catch (NoSuchColorException e) {
            e.printStackTrace();
        }
        assertEquals(1,c.getCurrNumOfStudents());
    }
}
