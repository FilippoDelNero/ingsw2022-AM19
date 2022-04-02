package it.polimi.ingsw.am19.Model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IslandList<T> implements Iterable<T>{
    private List<T> islands;
    private int length;

    public IslandList() {
        this.islands = new ArrayList<>();
        this.length = 0;
    }

    public IslandList(int size) {
        this.islands = new ArrayList<>(size);
        this.length = size;
    }

    public IslandList(List<T> list) {
        this.islands = new ArrayList<>(list);
        this.length = islands.size();
    }

    public void add(T t) {
        islands.add(t);
        length = islands.size();
    }

    public List<T> copy() {
        return List.copyOf(islands);
    }

    public int size() {
        return length;
    }

    @Override
    public ListIterator<T> iterator() {
        ListIterator<T> iterator = new ListIterator<T>() {
            private int currPos = -1;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                if(currPos < islands.size() - 1) {
                    currPos += 1;
                }
                else {
                    currPos = 0;
                }
                return islands.get(currPos);
            }

            @Override
            public boolean hasPrevious() {
                return true;
            }

            @Override
            public T previous() {
                if(currPos > 0) {
                    currPos -= 1;
                }
                else {
                    currPos = islands.size() - 1;
                }
                return islands.get(currPos);
            }

            @Override
            public int nextIndex() {
                int index;
                if(currPos < islands.size() - 1) {
                     index = currPos + 1;
                }
                else {
                    index = 0;
                }
                return index;
            }

            @Override
            public int previousIndex() {
                int index;
                if(currPos > 0) {
                    index = currPos - 1;
                }
                else {
                    index = islands.size() - 1;
                }
                return index;
            }

            @Override
            public void remove() {
                islands.remove(currPos);
                length = islands.size();
                currPos = previousIndex();
            }

            @Override
            public void set(T t) {
                add(t);
                currPos = nextIndex();
                currPos = nextIndex();
                remove();
            }

            @Override
            public void add(T t) {
                islands.add(currPos, t);
                currPos = previousIndex();
                length = islands.size();
            }
        };
        return iterator;
    }
}
