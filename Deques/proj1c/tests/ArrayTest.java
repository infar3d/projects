import deque.ArrayDeque;
import deque.Deque;
import deque.LinkedListDeque;
import deque.MaxArrayDeque;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

/** Performs some basic linked list tests. */
public class ArrayTest {
    @Test
    public void iterTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addLast(1); // [1]
        lld1.addFirst(2); // [2,1]
        lld1.addFirst(3); // [3,2,1]

        Iterator<Integer> iter = lld1.iterator();
        assertThat(iter.next()).isEqualTo(3);
    }
    @Test
    public void equalsTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();

        lld1.addLast(1); // [1]
        lld1.addFirst(2); // [2,1]
        lld1.addFirst(3); // [3,2,1]

        Deque<Integer> lld2 = new LinkedListDeque<>();

        lld1.addLast(1); // [1]
        lld1.addFirst(2); // [2,1]
        lld1.addFirst(3); // [3,2,1]

        Iterator<Integer> iter = lld1.iterator();
        assertThat(lld1.equals(lld2));
        assertThat(lld2.equals(lld1));
    }

    @Test
    public void toStringtest(){
        Deque<String> lld1 = new ArrayDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("last");

        assertThat(lld1.toString()).isEqualTo("[front, middle, last]");
    }

}



