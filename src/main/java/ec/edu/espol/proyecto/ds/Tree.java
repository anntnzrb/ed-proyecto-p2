package ec.edu.espol.proyecto.ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Tree<E> {

    private TreeNode<E> root;

    public TreeNode<E> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<E> root) {
        this.root = root;
    }

    public TreeNode<E> buscarNodo(E data) {
        return buscarNodo(data, root);
    }

    private TreeNode<E> buscarNodo(E data, TreeNode<E> n) {
        if (n == null) {
            return n;
        } else if ((n.data).equals(data)) {
            return n;
        }
        for (TreeNode<E> nod : n.getChildren()) {
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
            lData.add(nd.getData());
        });
        Collections.sort((List)lData);
        return lData;
    }

    public static final class TreeNode<E> {

        private E data;
        private List<TreeNode<E>> children;
        
        /* constructores */

        public TreeNode(final E data) {
            this.data = data;
            children = new LinkedList<>();
        }

        /* getters & setters */
        public E getData() {
            return data;
        }

        public List<TreeNode<E>> getChildren() {
            return children;
        }
        
        
        
    }
    
    
    
}
