package ec.edu.espol.proyecto.ds;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Tree<E> {
    private TreeNode<E> root;

    /* constructores */
    public Tree() { }

    public TreeNode<E> getRoot() {
        return root;
    }

    public void setRoot(final TreeNode<E> root) {
        this.root = root;
    }

    private TreeNode<E> findNode(final E data) {
        return findNode(data, root);
    }

    private TreeNode<E> findNode(final E data, final TreeNode<E> treeNode) {
        if (data == null || treeNode == null) { return null; }
        if ((treeNode.getData()).equals(data)) { return treeNode; }

        return treeNode.getChildren()
                       .stream()
                       .map(nod -> findNode(data, nod))
                       .filter(Objects::nonNull)
                       .findFirst()
                       .orElse(null);
    }

    public List<E> getUtilidadMax(final E data) {
        if (data == null) { return null; }

        final TreeNode<E> treeNode = findNode(data);
        if (treeNode == null) { return null; }

        return treeNode.getChildren()
                       .stream()
                       .map(TreeNode::getData)
                       .collect(Collectors.toCollection(LinkedList::new));
    }

    public static final class TreeNode<E> {
        private final E                 data;
        private final List<TreeNode<E>> children;

        /* constructores */

        public TreeNode(final E data) {
            this.data = data;
            children = new LinkedList<>();
        }

        /* getters & setters */
        E getData() {
            return data;
        }

        List<TreeNode<E>> getChildren() {
            return children;
        }
    }
}