package ec.edu.espol.proyecto.ds;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public final class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;

    /* constructores */
    public Tree(final T board) {
        root = new TreeNode<>(board);
    }

    /**
     * Verifica si la data pasada por parámetro se encuentra en el árbol.
     *
     * @param data data a buscar en el árbol
     * @return {@code true} si la data se encuentra
     */
    public boolean contains(final T data) {
        final TreeNode<T> nodo = findNode(data, root);
        return nodo != null;
    }

    /**
     * Agrega un par (padres e hijo) en el árbol.
     *
     * @param child  hijo a agregar
     * @param parent padre a agregar
     * @return {@code true} si la inserción es exitosa
     */
    public boolean add(final T child, final T parent) {
        TreeNode<T> childNode = findNode(child);
        if (childNode != null) { return false; }

        childNode = new TreeNode<>(child);
        if (root == null && parent == null) {
            root = childNode;
            return true;
        }

        final TreeNode<T> parentNode = findNode(parent);
        if (parentNode != null) {
            parentNode.addChild(childNode);
            return true;
        }

        return false;
    }

    /**
     * Busca un elemento en un nodo específico.
     *
     * @param data data a buscar en el nodo
     * @param node nodo en el cual buscar
     * @return nodo donde se encuentra la data
     */
    private TreeNode<T> findNode(final T data, final TreeNode<T> node) {
        if (node == null) { return node; }
        if (node.getData().equals(data)) { return node; }

        return node.getChildren()
                   .stream()
                   .map(n -> findNode(data, n))
                   .filter(Objects::nonNull)
                   .findFirst()
                   .orElse(null);
    }

    /**
     * Wrapper de {@link #findNode(Comparable, TreeNode)}.
     */
    public TreeNode<T> findNode(final T data) {
        return findNode(data, root);
    }

    /**
     * Retorna la utilidad máxima a partir de una data, ésto es posible mediante
     * el ordenamienta de la lista que contiene a los elementos.
     *
     * @param data data a analizar
     * @return lista de nodos
     */
    public List<T> getMaxUtility(final T data) {
        final TreeNode<T> node = findNode(data);
        if (node == null) { return null; }

        final List<T> xs = new LinkedList<>();
        node.getChildren().forEach(n -> xs.add(n.getData()));
        Collections.sort(xs);

        return xs;
    }
}