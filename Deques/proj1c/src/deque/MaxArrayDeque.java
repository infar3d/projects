package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comp;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        } else {
            Iterator<?> iter = iterator();
            T currMax = (T) iter.next();

            int i = 0;

            while (i < size() - 1) {
                T curr = (T) iter.next();
                if (comp.compare(currMax, curr) < 0) {
                    currMax = curr;
                }
                i++;
            }
            return currMax;
        }
    }

    public T max(Comparator<T> c) {
        comp = c;
        return max();
    }

}
