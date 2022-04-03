package it.polimi.ingsw.am19.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * kind of list that will be used to store islands
 * it is a circular list
 * @param <T> the object the list will host
 */
public class IslandList<T> implements Iterable<T>{
    /**
     * a list of T
     */
    private final List<T> islands;

    /**
     * standard constructor
     */
    public IslandList() {
        this.islands = new ArrayList<>();
    }

    /**
     * constructor that builds an islandList of a certain initial size
     * @param size the initial length of the islandList
     */
    public IslandList(int size) {
        this.islands = new ArrayList<>(size);
    }

    /**
     * constructor that builds an IslandList starting from an existing list
     * @param list the list you want to convert into islandList
     */
    public IslandList(List<T> list) {
        this.islands = new ArrayList<>(list);
    }

    /**
     * method to add an element to the list
     * @param t the element you want to add to the list
     */
    public void add(T t) {
        islands.add(t);
    }

    /**
     * getter for the list
     * @return a list copy of the islandList
     */
    public List<T> copy() {
        return List.copyOf(islands);
    }

    /**
     * tells you how many elements you have
     * @return the length of the islandList
     */
    public int size() {
        return islands.size();
    }

    /**
     * the islandList iterator
     * @return an iterator for the islandList
     */
    @Override
    public ListIterator<T> iterator() {
        ListIterator<T> iterator = new ListIterator<T>() {
            /**
             * stores the current position
             */
            private int currPos = -1;

            /**
             * is a circular list, there will always be a next element
             * unless the list is empty
             * @return true unless the list is empty
             */
            @Override
            public boolean hasNext() {
                return islands.size() != 0;
            }

            /**
             * moves the index forward by one and return the element
             * @return the next object
             */
            @Override
            public T next() throws NoSuchElementException {
                if(!hasNext())
                    throw new NoSuchElementException();

                if(currPos < islands.size() - 1) {
                    currPos += 1;
                }
                else {
                    currPos = 0;
                }
                return islands.get(currPos);
            }

            /**
             * is a circular list, there will always be a previous element
             * unless the list is empty
             * @return true unless the list is empty
             */
            @Override
            public boolean hasPrevious() {
                return islands.size() != 0;
            }

            /**
             * moves the index backward by one and return the element
             * @return the previous object
             */
            @Override
            public T previous() throws NoSuchElementException {
                if(!hasPrevious())
                    throw new NoSuchElementException();

                if(currPos > 0) {
                    currPos -= 1;
                }
                else {
                    currPos = islands.size() - 1;
                }
                return islands.get(currPos);
            }

            /**
             * moves the index forward by one
             * @return the next index
             */
            @Override
            public int nextIndex() throws NoSuchElementException {
                int index;

                if(!hasNext())
                    throw new NoSuchElementException();

                if(currPos < islands.size() - 1) {
                     index = currPos + 1;
                }
                else {
                    index = 0;
                }
                return index;
            }

            /**
             * moves the index backward by one
             * @return the previous index
             */
            @Override
            public int previousIndex() throws NoSuchElementException {
                int index;

                if(!hasPrevious())
                    throw new NoSuchElementException();

                if(currPos > 0) {
                    index = currPos - 1;
                }
                else {
                    index = islands.size() - 1;
                }
                return index;
            }

            /**
             * removes the current objects, the index will point to the element before
             * if i remove el #8, the index will point to el #7, the next will return the new el #8
             */
            @Override
            public void remove() throws NoSuchElementException {
                if(currPos == -1)
                    throw new NoSuchElementException();

                islands.remove(currPos);
                currPos = previousIndex();
            }

            /**
             * overrides the objects in current position with the object t
             * @param t the object the will be set instead of the current element
             */
            @Override
            public void set(T t) throws NoSuchElementException{
                if(currPos == -1)
                    throw new NoSuchElementException();

                add(t);
                currPos = nextIndex();
                currPos = nextIndex();
                remove();
            }

            /**
             * add an element in current position, the index will than be moved backward,
             * asking the next element will return the newly added one
             * @param t the object you want to add to the list
             */
            @Override
            public void add(T t) {
                if (currPos == -1)
                    currPos = 0;
                islands.add(currPos, t);
                currPos = previousIndex();
            }
        };

        return iterator;
    }
}
