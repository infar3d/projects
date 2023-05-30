import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    @Test

    public void addFirstBasic(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(4); // [4]
        ad1.addFirst(3); // [3,4]
        ad1.addFirst(2); // [2,3,4]
        ad1.addFirst(1); // [1,2,3,4]

        assertThat(ad1.toList()).containsExactly(1,2,3,4).inOrder();

    }
    @Test
    public void addLastBasic() {
        Deque<Integer> ad1 = new ArrayDeque<>();

        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);

        assertThat(ad1.toList()).containsExactly(1,2,3,4).inOrder();
    }
    @Test
    public void addFirstAndLastBasic(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(4); // [4]
        ad1.addLast(5); // [4,5]
        ad1.addFirst(2); // [2,4,5]
        ad1.addLast(1); // [2,4,5,1]
        ad1.addFirst(3); // [3,2,4,5,1]
        ad1.addFirst(7); // [7,3,2,4,5,1]

        assertThat(ad1.toList()).containsExactly(7,3,2,4,5,1).inOrder();

    }
    @Test
    public void addFirstAfterRemove(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(4); // [4]
        ad1.addFirst(5); // [5,4]
        ad1.addFirst(2); // [2,4,5]
        ad1.removeLast(); // [2,4]
        ad1.removeLast(); // [2]
        ad1.removeLast(); // []
        assertThat(ad1.isEmpty()).isTrue();
        ad1.addFirst(7); // [7]
        ad1.addFirst(8); // [8,7]
        ad1.addFirst(9); // [9,8,7]

        assertThat(ad1.get(0)).isEqualTo(9);
    }

    @Test
    public void addLastAfterRemove(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(4); // [4]
        ad1.addFirst(5); // [5,4]
        ad1.addFirst(2); // [2,4,5]
        ad1.removeLast(); // [2,4]
        ad1.removeLast(); // [2]
        ad1.removeLast(); // []
        assertThat(ad1.isEmpty()).isTrue();
        ad1.addLast(7); // [7]
        ad1.addLast(8); // [7,8]
        ad1.addLast(9); // [7,8,9]

        assertThat(ad1.get(0)).isEqualTo(7);
    }
    @Test
    public void isEmptyFalse(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(4); // [4]
        ad1.addLast(5); // [4,5]
        ad1.addFirst(2); // [2,4,5]
        ad1.addLast(1); // [2,4,5,1]

        assertThat(ad1.isEmpty()).isFalse();
    }

    @Test
    public void isEmptyTrue(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.isEmpty()).isTrue();
    }

    @Test
    public void toList(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        assertThat(ad1.toList()).isEmpty();
    }

    @Test

    public void removeFirst1(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(4); // [4]
        ad1.addLast(5); // [4,5]
        ad1.addFirst(2); // [2,4,5]
        ad1.addLast(1); // [2,4,5,1]
        ad1.addFirst(3); // [3,2,4,5,1]
        ad1.addFirst(7); // [7,3,2,4,5,1]

        assertThat(ad1.removeFirst()).isEqualTo(7);

    }
    @Test
    public void removeFirstToEmpty(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(4); // [4]
        ad1.addFirst(5); // [5,4]
        ad1.addFirst(2); // [2,4,5]
        ad1.removeFirst(); // [4,5]
        ad1.removeFirst(); // [5]
        ad1.removeFirst(); // []
        assertThat(ad1.size()).isEqualTo(0);

    }

    @Test

    public void removeFirstToOne(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(4); // [4]
        ad1.addFirst(5); // [5,4]
        ad1.addFirst(2); // [2,4,5]
        ad1.removeFirst(); // [4,5]
        ad1.removeFirst(); // [5]
        assertThat(ad1.size()).isEqualTo(1);
    }

    @Test
    public void removeLast1(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(4); // [4]
        ad1.addLast(5); // [4,5]
        ad1.addFirst(2); // [2,4,5]
        ad1.addLast(1); // [2,4,5,1]
        ad1.addFirst(3); // [3,2,4,5,1]
        ad1.addFirst(7); // [7,3,2,4,5,1]

        assertThat(ad1.removeLast()).isEqualTo(1);
    }
    @Test
    public void resizeUp(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(4); // [4]
        ad1.addLast(5); // [4,5]
        ad1.addFirst(2); // [2,4,5]
        ad1.addLast(1); // [2,4,5,1]
        ad1.addFirst(3); // [3,2,4,5,1]
        ad1.addFirst(7); // [7,3,2,4,5,1]
        ad1.addLast(8); // [7,3,2,4,5,1,8]
        ad1.addFirst(9); // [9,7,3,2,4,5,1,8]
        ad1.addFirst(10); // [10,9,7,3,2,4,5,1,8]

        assertThat(ad1.removeFirst()).isEqualTo(10); // [9,7,3,2,4,5,1,8]
        assertThat(ad1.removeLast()).isEqualTo(8); // [9,7,3,2,4,5,1]

    }
    @Test
    public void resizeUp2(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i=0; i<18; i++){
            ad1.addLast(i); // [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]
        }
        assertThat(ad1.removeLast()).isEqualTo(17);
        assertThat(ad1.toList()).containsExactly(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16).inOrder();
    }
    @Test
    public void resizeUp3() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 18; i > 0; i--) {
            ad1.addFirst(i); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        }
        assertThat(ad1.removeFirst()).isEqualTo(1);
        assertThat(ad1.removeLast()).isEqualTo(18);
        assertThat(ad1.toList()).containsExactly(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17).inOrder();
    }
    @Test

    public void resizeDown1() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 18; i > 0; i--) {
            ad1.addFirst(i); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        }

        for (int i = 0; i < 10; i++) {
            ad1.removeLast(); // [1,2,3,4,5,6,7,8]
        }
            assertThat(ad1.size()).isEqualTo(8);
            assertThat(ad1.removeFirst()).isEqualTo(1);
            assertThat(ad1.removeLast()).isEqualTo(8);
        }

    @Test

    public void resizeDownAndUp() {
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 18; i > 0; i--) {
            ad1.addFirst(i); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        }

        for (int i = 0; i < 10; i++) {
            ad1.removeLast(); // [1,2,3,4,5,6,7,8]
        }
        for (int i = 25; i > 19; i--) {
            ad1.addFirst(i); // [20,21,22,23,24,25,1,2,3,4,5,6,7,8]
        }

        assertThat(ad1.toList()).containsExactly(20, 21, 22, 23, 24, 25, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();
        assertThat(ad1.removeFirst()).isEqualTo(20);
        assertThat(ad1.removeLast()).isEqualTo(8);
    }
    @Test
    public void getBasic(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(4); // [4]
        ad1.addLast(5); // [4,5]
        ad1.addFirst(2); // [2,4,5]
        ad1.addLast(1); // [2,4,5,1]
        ad1.addFirst(3); // [3,2,4,5,1]
        ad1.addFirst(7); // [7,3,2,4,5,1]

        assertThat(ad1.get(4)).isEqualTo((5));
    }
    @Test

    public void getBigger(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 18; i > 0; i--) {
            ad1.addFirst(i); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        }
        assertThat(ad1.get(6)).isEqualTo(7);
        assertThat(ad1.get(17)).isEqualTo(18);

    }
    @Test
    public void getNegative(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 18; i > 0; i--) {
            ad1.addFirst(i); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        }
        assertThat(ad1.get(-1)).isNull();
    }

    @Test
    public void getLarge(){
        Deque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 18; i > 0; i--) {
            ad1.addFirst(i); // [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        }
        assertThat(ad1.get(50)).isNull();
    }
}

