package ec.edu.espol.proyecto.ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Tree<E> {

    private TreeNode<E> root;

    /* constructores */
    public Tree(E board) {
        root = new TreeNode(board);
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    public void setRoot(final TreeNode<E> root) {
        this.root = root;
    }

    public TreeNode<E> findNode(E data) {
        return findNode(data, root);
    }

    private TreeNode<E> findNode(E data, TreeNode<E> treeNode) {
        if (treeNode == null) {
            return null;
        }
        if ((treeNode.getData()).equals(data)) {
            return treeNode;
        }

        for (TreeNode<E> nod : treeNode.getChildren()) {
            TreeNode<E> nL = findNode(data, nod);
            if (nL != null) {
                return nL;
            }
        }
        return null;
    }

    public List<E> getUtilidadMax(final E data) {
        TreeNode<E> treeNode = this.findNode(data);
        if (treeNode == null) {
            return null;
        }
        List<E> lData = new LinkedList<>();
        treeNode.getChildren().forEach((nd) -> {
            lData.add(nd.getData());
        });
        return lData;
    }

    public boolean add(E parent, E child) {
        TreeNode<E> TreeNode = findNode(child);
        if (TreeNode != null) {
            return false;
        }
        TreeNode = new TreeNode<>(child);
        if (root == null && parent == null) {
            this.root = TreeNode;
            return true;
        }

        TreeNode<E> parentNode = findNode(parent);
        if (parentNode != null) {
            parentNode.addChild(TreeNode);
            return true;
        }

        return false;
    }

    //__________________________________________________________
    public class TreeNode<E> {

        private E data;
        private List<TreeNode<E>> children;

        /* constructores */
        public TreeNode(final E data) {
            this.data = data;
            this.children = new ArrayList<>();
        }

        public TreeNode(TreeNode<E> node) {
            this.data = (E) node.getData();
            children = new ArrayList<>();
        }

        /* getters & setters */
        public E getData() {
            return data;
        }

        List<TreeNode<E>> getChildren() {
            return children;
        }

        public void addChild(TreeNode<E> child) {
            if (data == null) {
                this.data = child.data;
            } else {
                children.add(child);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }
            if (obj instanceof TreeNode) {
                if (((TreeNode<?>) obj).getData().equals(this.data)) {
                    return true;
                }
            }
            return false;
        }
    }

}
