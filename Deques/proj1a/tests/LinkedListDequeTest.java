import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
    void noNonTrivialFields() {
        Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
        List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
    }

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque.
    @Test
    public void addFirstandLastTestWritten() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(4); // [4]
        lld1.addFirst(3); // [3,4]
        lld1.addLast(7); // [3,4,7]
        lld1.addFirst(2); // [2,3,4,7]

        assertThat(lld1.toList()).containsExactly(2, 3, 4, 7).inOrder();
    }

    @Test

    public void toListEmpty(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.toList()).isEmpty();
    }
    @Test
    public void addFirstTestWritten() {
        Deque<String> lld1 = new LinkedListDeque<>();
        // the quick brown fox
        lld1.addFirst("fox");
        lld1.addFirst("brown");
        lld1.addFirst("quick");
        lld1.addFirst("the");

        assertThat(lld1.toList()).containsExactly("the", "quick", "brown", "fox").inOrder();
    }

    @Test
    public void addLastTestWritten() {
        Deque<String> lld1 = new LinkedListDeque<>();
        // this is a test sentence
        lld1.addLast("this");
        lld1.addLast("is");
        lld1.addLast("a");
        lld1.addLast("test");
        lld1.addLast("sentence");
        assertThat(lld1.toList()).containsExactly("this", "is", "a", "test", "sentence").inOrder();

    }
    // isEmpty and size tests
    @Test
    public void isEmptyTrue() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void isEmptyFalse() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(5);
        lld1.addFirst(4);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    public void sizeZeroTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void sizeTest() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 500; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.size()).isEqualTo(500);
    }
    // get iterative tests
    @Test

    public void getTestOutofBounds() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.get(30)).isEqualTo(null);
    }

    @Test
    public void getTestNegative() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.get(-40)).isEqualTo(null);
    }

    @Test
    public void getTest1() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 500; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.get(399)).isEqualTo(399);
    }

    @Test
    public void getTest2() {

        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("one");
        lld1.addLast("two");
        lld1.addLast("three");
        lld1.addLast("four");
        lld1.addLast("five");

        assertThat(lld1.get(4)).isEqualTo("five");
    }

    // get recursive tests

    @Test

    public void getTestOutofBoundsRecur() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.getRecursive(30)).isEqualTo(null);
    }

    @Test
    public void getTestNegativeRecur() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.getRecursive(-40)).isEqualTo(null);
    }

    @Test
    public void getTest1Recur() {
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 500; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.getRecursive(399)).isEqualTo(399);
    }

    @Test
    public void getTest2Recur() {

        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("one");
        lld1.addLast("two");
        lld1.addLast("three");
        lld1.addLast("four");
        lld1.addLast("five");

        assertThat(lld1.getRecursive(4)).isEqualTo("five");
    }

// remove last and add last tests
    @Test

    public void emptyRemoveFirstAndLast(){
        Deque<String> lld1 = new LinkedListDeque<>();

        assertThat(lld1.removeFirst()).isEqualTo(null);
        assertThat(lld1.removeLast()).isEqualTo(null);
    }
    @Test

    public void removeFirstBasic(){
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("one");
        lld1.addLast("two");
        lld1.addLast("three");
        lld1.addLast("four");
        lld1.addLast("five");

        assertThat(lld1.removeFirst()).isEqualTo("one");
        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld1.toList().get(0)).isEqualTo("two");
    }
    @Test
    public void removeLastBasic(){
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("one");
        lld1.addLast("two");
        lld1.addLast("three");
        lld1.addLast("four");
        lld1.addLast("five");

        assertThat(lld1.removeLast()).isEqualTo("five");
        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld1.toList().get(lld1.size() -1)).isEqualTo("four");

}
    @Test
    public void removeFirstAndLast(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 500; i++) {
            lld1.addLast(i); }

        assertThat(lld1.removeFirst()).isEqualTo(0);
        assertThat(lld1.removeLast()).isEqualTo(499);
        assertThat(lld1.size()).isEqualTo(498);
    }

    @Test
    public void addFirstAfterRemoving(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(1);
        assertThat(lld1.removeFirst()).isEqualTo(1);
        assertThat(lld1.size()).isEqualTo(0);
        lld1.addFirst(0);
        assertThat(lld1.get(0)).isEqualTo(0);
        assertThat(lld1.size()).isEqualTo(1);
    }

    @Test
    public void removeLastToEmpty(){
        Deque<Integer> lld1 = new LinkedListDeque<>();

        lld1.addLast(5);

        assertThat(lld1.removeLast()).isEqualTo(5);
        assertThat(lld1.size()).isEqualTo(0);

    }

    @Test
    public void removeFirstToEmpty(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(5);
        assertThat(lld1.removeFirst()).isEqualTo(5);
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void removeFromEmpty(){
        Deque<String> lld1 = new LinkedListDeque<>();

        assertThat(lld1.removeLast()).isEqualTo(null);
        assertThat(lld1.removeFirst()).isEqualTo(null);
    }

    @Test
    public void removeLastABunch(){
        Deque<Integer> lld1 = new LinkedListDeque<>();

        for (int i=0; i<25; i++) {
            lld1.addLast(i);
        }
        for (int i=0; i<22; i++) {
            lld1.removeLast();
        }
        assertThat(lld1.size()).isEqualTo(3);
        assertThat(lld1.get(0)).isEqualTo(0);

    }

    @Test
    public void removeFirstToOne(){
        Deque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(2);
        lld1.addLast(1);

        lld1.removeFirst();

        assertThat(lld1.get(0)).isEqualTo(1);
        assertThat(lld1.size()).isEqualTo(1);
    }
    }