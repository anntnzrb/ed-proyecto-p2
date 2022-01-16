package ec.edu.espol.proyecto.ds;

import java.util.ArrayList;
import java.util.List;


public final class TreeNode<T> {
    private final List<TreeNode<T>> children;
    private       T                 data;

    /* constructores */
    TreeNode(final T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    /**
     * Agrega un hijo nuevo en la lista de nodos.
     *
     * @param child nodo hijo a agregar
     */
    void addChild(final TreeNode<T> child) {
        if (data == null) {
            data = child.getData();
        } else {
            children.add(child);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (null == obj) { return false; }

        return obj instanceof TreeNode && ((TreeNode<?>) obj).getData().equals(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    /* getters & setters */
    public T getData() {
        return data;
    }

    List<TreeNode<T>> getChildren() {
        return children;
    }
}
