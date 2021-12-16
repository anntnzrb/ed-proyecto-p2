package ec.edu.espol.proyecto.tda;

import java.util.LinkedList;
import java.util.List;

public final class Tree<E> {
    private TreeNode<E> root;

    /* constructores */
    public Tree() {}

    private static final class TreeNode<E> {
        private E             data;
        private List<Tree<E>> children;

        /* constructores */
        public TreeNode() {}

        public TreeNode(E data) {
            this.data = data;
            this.children = new LinkedList<>();
        }

        /* getters & setters */
        public E getData() {
            return this.data;
        }
    }
}
