
import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;

    private int nextFirst;
    private int nextLast;

    private static final int MIN_RESIZE_UP = 16;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        size++;
        nextFirst = nextNextFirst(nextFirst);
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        nextLast = nextNextLast(nextLast);
        size++;
    }


    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = 0;
        int x = nextFirst;
        while (index < size) {
            T curr = items[prevFirst(x)];
            returnList.add(curr);
            x = prevFirst(x);
            index++;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T x = items[prevFirst(nextFirst)];
        items[prevFirst(nextFirst)] = null;
        nextFirst = prevFirst(nextFirst);
        size--;
        if (size >= MIN_RESIZE_UP && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return x;

    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T x = items[prevLast(nextLast)];
        items[prevLast(nextLast)] = null;
        nextLast = prevLast(nextLast);
        size--;
        if (size >= MIN_RESIZE_UP && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int x = prevFirst(nextFirst);
        int i = index;
        while (i != 0) {
            x = prevFirst(x);
            i--;
        }
        return items[x];
    }


    /** HELPERS */
    public void resize(int newSize) {
        T[] temp = (T[]) new Object[newSize];

        int index = 0;
        int x = nextFirst;
        while (index < size) {
            T curr = items[prevFirst(x)];
            temp[index] = (curr);
            x = prevFirst(x);
            index++;
        }
        items = temp;
        nextLast = size;
        nextFirst = items.length - 1;
    }

    private int prevFirst(int x) {
        if (x == items.length - 1) {
            return 0;
        } else {
            return x + 1;
        }
    }

    private int prevLast(int x) {
        if (x == 0) {
            return items.length - 1;
        }
        return x - 1;
    }

    private int nextNextFirst(int x) {
        if (x == 0) {
            return items.length - 1;
        } else {
            return x - 1;
        }
    }

    private int nextNextLast(int x) {
        if (x == items.length - 1) {
            return  0;
        } else {
            return x + 1;
        }
    }

    /** MAIN */
    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
    }
}
