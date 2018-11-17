package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    private Node<T> root = null;

    private int size = 0;

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //Трудоёмкость Т = O(N)
    //Ресурсоёмкость R = O(1)
    @Override
    public boolean remove(Object o) {
        root = removeNode(root, o);
        size--;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    public Node<T> removeNode(Node<T> root, Object o) {
        if (root == null) {
            return null;
        }
        if (o.equals(root.value)) {
            if ((root.left != null) && (root.right != null)) {
                root.value = minNode(root.right);
                root.right = removeNode(root.right, root.value);
            } else {
                if (root.left != null) {
                    return root.left;
                } else {
                    return root.right;
                }
            }
        }
        T To;
        To = (T) o;
        if (To.compareTo(root.value) < 0) {
            root.left = removeNode(root.left, o);
        } else {
            root.right = removeNode(root.right, o);
        }
        return root;
    }

    private T minNode(Node<T> node) {
        T minNode = null;
        if (node.left == null) {
            return node.value;
        } else {
            while (node.left != null) {
                minNode = node.left.value;
                node = node.left;
            }
        }
        return minNode;
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current;
        private Deque<Node<T>> stack = new LinkedList<>();

        private BinaryTreeIterator() {
            current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        //Трудоёмкость Т = O(N)
        //Ресурсоёмкость R = O(1)
        private Node<T> findNext() {
            return stack.pop();
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node;
            current = findNext();
            node = current;
            if (current == null) throw new NoSuchElementException();
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        //Трудоёмкость Т = O(N)
        //Ресурсоёмкость R = O(1)
        @Override
        public void remove() {
            root = BinaryTree.this.removeNode(root, current.value);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
