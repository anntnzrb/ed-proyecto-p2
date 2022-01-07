package ec.edu.espol.proyecto.ds;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Tree<E> {

    private TreeNode<E> root;

    public boolean existe(E clave) {
        TreeNode<E> nodo = buscarNodo(clave, this);
        return nodo != null;
    }


    public TreeNode<E> buscarNodo(E data) {
        return buscarNodo(data, this);
    }

    private TreeNode<E> buscarNodo(E data, Tree<E> n) {
        if (n.root.data == null) {
            return n.root;
        } else if ((n.root.data).equals(data)) {
            return n.root;
        }
        for (Tree<E> nod : n.root.getChildren()) {
            TreeNode<E> nL = buscarNodo(data, nod);
            if (nL != null) {
                return nL;
            }
        }
        return null;
    }

    public List<E> getUtilidadMax(E data) {
        TreeNode<E> n = this.buscarNodo(data);
        if (n == null) {
            return null;
        }
        List<E> lData = new LinkedList<>();
        n.getChildren().forEach((nd) -> {
            lData.add(nd.root.getData());
        });
        Collections.sort((List) lData);
        return lData;
    }

    private static final class TreeNode<E> {

        private E data;
        private List<Tree<E>> children;

        /* constructores */
        public TreeNode() {
        }

        public TreeNode(final E data) {
            this.data = data;
            children = new LinkedList<>();
        }

        /* getters & setters */
        public E getData() {
            return data;
        }

        public List<Tree<E>> getChildren() {
            return children;
        }
    }
}
