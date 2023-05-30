package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private int len = 0;
    private DoubleNode sentinel;
    private class DoubleNode {
        private T value;
        private DoubleNode prev;
        private DoubleNode next;

        public DoubleNode(T value, DoubleNode prev, DoubleNode next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        sentinel = new DoubleNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        DoubleNode oldFirst = sentinel.next;
        DoubleNode newFirst = new DoubleNode(x, sentinel, oldFirst);
        sentinel.next = newFirst;
        oldFirst.prev = newFirst;
        len++;
    }

    @Override
    public void addLast(T x) {
        DoubleNode oldLast = sentinel.prev;
        DoubleNode newLast = new DoubleNode(x, oldLast, sentinel);
        sentinel.prev = newLast;
        oldLast.next = newLast;
        len++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        DoubleNode currNode = sentinel.next;
        while (currNode != sentinel) {
            returnList.add(currNode.value);
            currNode = currNode.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (len == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public T removeFirst() {

        if (len == 0) {
            return null;
        } else {
            DoubleNode oldFirst = sentinel.next;
            DoubleNode newFirst = oldFirst.next;

            sentinel.next = newFirst;
            newFirst.prev = sentinel;
            len--;
            return oldFirst.value;
        }
    }

    @Override
    public T removeLast() {
        if (len == 0) {
            return null;
        } else {
            DoubleNode oldLast = sentinel.prev;
            DoubleNode newLast = oldLast.prev;

            sentinel.prev = newLast;
            newLast.next = sentinel;
            len--;
            return oldLast.value;

        }
    }

    @Override
    public T get(int index) {
        if (index > len - 1  || index < 0) {
            return null;
        } else {
            int i = 0;
            DoubleNode currNode = sentinel.next;
            while (i != index) {
                currNode = currNode.next;
                i++;

            }
            return currNode.value;

        }
    }

    @Override
    public T getRecursive(int index) {
        return getRecurHelper(sentinel.next, index);
    }
    private T getRecurHelper(DoubleNode node, int index) {
        if (index > len - 1 || index < 0) {
            return null;
        }
        if (index == 0) {
            return node.value;
        } else {
            return getRecurHelper(node.next, index - 1);
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    public class LinkedListIterator implements Iterator<T> {
        private int pos;
        public LinkedListIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < len;
        }
        public T next() {
            T returnItem = get(pos);
            pos++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Deque<?> otherDeque) {
            Iterator<?> iter = otherDeque.iterator();
            if (len == otherDeque.size()) {
                for (T x: this) {
                    if (!x.equals(iter.next())) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }
    @Override
    public String toString() {
        return toList().toString();
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(2);
        lld.addFirst(1);
    }
}



