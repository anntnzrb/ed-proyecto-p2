package ec.edu.espol.proyecto.ds;

import java.util.Comparator;

public final class Tree<E> {
    private final E             data;
    private final Heap<Tree<E>> children;
    private final Comparator<E> cmp;

    /* constructores */
    Tree(final E data, final Comparator<E> cmp) {
        this.data = data;
        this.cmp = cmp;
        children = new Heap<>((o1, o2) -> cmp.compare(o1.getData(), o2.getData()));
    }

    public Tree<E> add(final E data) {
        children.offer(new Tree<>(data, cmp));

        return this;
    }

    /* getters & setters */
    public E getData() {
        return data;
    }

    public Heap<Tree<E>> getChildren() {
        return children;
    }
}
