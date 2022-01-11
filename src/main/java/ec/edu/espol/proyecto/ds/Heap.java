package ec.edu.espol.proyecto.ds;

import java.util.Arrays;
import java.util.Comparator;

public final class Heap<E> {
    /**
     * Capacidad inicial del arreglo.
     */
    public static final int INIT_CAPACITY = 10;

    /**
     * Filtro para ordernar los elementos.
     */
    private final Comparator<E> cmp;

    /**
     * Arreglo que contiene los elementos a agregar.
     */
    private E[] queue;

    /**
     * Cantidad de elementos que puede almacenar el arreglo.
     */
    private int capacity;

    /**
     * Cantidad de elementos que tiene el arreglo.
     */
    private int size;

    /**
     * Define el ordenamiento de los elementos.
     * <p>
     * {@code true} para ordenamiento descendente
     * {@code false} para ordenamiento ascendente
     */
    private final boolean isMax;

    /* constructores */
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public Heap(final Comparator<E> cmp, final boolean isMax,
                final int initCapacity) {
        queue = (E[]) new Comparable[capacity = initCapacity];
        this.isMax = isMax;
        this.cmp = this.isMax ? cmp : cmp.reversed();

        size = 0;
    }

    public Heap(final Comparator<E> cmp) {
        this(cmp, false, INIT_CAPACITY);
    }

    public Heap(final Comparator<E> cmp, final boolean isMax) {
        this(cmp, isMax, INIT_CAPACITY);
    }

    /**
     * Verifica si el arreglo está vacío.
     *
     * @return {@code true} si el arreglo está vacío,
     * {@code false} caso contrario
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Verifica si el arreglo está lleno.
     *
     * @return {@code true} si el arreglo está lleno,
     * {@code false} caso contrario
     */
    private boolean isFull() {
        return capacity == size;
    }

    /**
     * Retorna el número de elementos en la colección.
     *
     * @return el número de elementos en la colección
     */
    public int size() {
        return size;
    }

    /**
     * Verifica si el elemento dado a partir del índice es una hoja.
     *
     * @param idx Índice a verificar
     * @return {@code true} si el elemento es una hoja,
     * {@code false} caso contrario
     */
    private boolean isLeaf(final int idx) {
        return idx > (size >> 1) - 1;
    }

    /**
     * Inserta el elemento pasado en el {@link Heap}.
     *
     * @param e elemento a insertar al final del arreglo
     * @return el mismo {@link Heap} (para concatenar operaciones)
     */
    public Heap<E> offer(final E e) {
        if (e == null) { return null; }

        // aumentar capacidad si es necesario
        if (isFull()) { grow(); }

        queue[size++] = e;
        fixUpward();

        return this;
    }

    /**
     * Retorna el mayor (o menor) elemento del {@link Heap}.
     *
     * @return el mayor (o menor) elemento del {@link Heap}.
     */
    public E peek() {
        if (isEmpty()) { return null; }

        return queue[0];
    }

    /**
     * Remueve todos los elementos del arreglo.
     */
    public void clear() {
        if (isEmpty()) { return; }

        Arrays.fill(queue, null);

        size = 0;
    }

    /**
     * Remueve y retorna el mayor (o menor) elemento del {@link Heap}.
     *
     * @return elemento eliminado
     */
    public E poll() {
        if (isEmpty()) { return null; }

        final E oldVal = queue[0];
        swap(0, --size);
        fixDownward(0);

        return oldVal;
    }

    /**
     * Arregla el ordenamiento del Heap hacia abajo.
     *
     * @param idx índice del root a arreglar
     */
    private void fixDownward(final int idx) {
        // no hay nada que arreglar si es hoja
        if (isLeaf(idx)) { return; }

        int maxIdx = idx;
        final int leftIdx = getLeftIdx(idx);
        final int rightIdx = getRightIdx(idx);

        // verificar y cambiar si el hijo izq. es mayor a su padre
        if (checkRange(leftIdx)
            && cmp.compare(queue[leftIdx], queue[maxIdx]) > 0) {
            maxIdx = leftIdx;
        }

        // verificar y cambiar si el hijo der. es mayor a su padre
        if (checkRange(rightIdx)
            && cmp.compare(queue[rightIdx], queue[maxIdx]) > 0) {
            maxIdx = rightIdx;
        }

        // intercambiar si alguno de los hijos es mayor al padre
        if (maxIdx != idx) {
            swap(maxIdx, idx);
            fixDownward(maxIdx);
        }
    }

    /**
     * Arregla el ordenamiento del Heap hacia arrib.
     */
    private void fixUpward() {
        for (int i = (size >> 1) - 1; i >= 0; --i) {
            fixDownward(i);
        }
    }

    private int getLeftIdx(final int idx) {
        return (idx << 1) + 1;
    }

    private int getRightIdx(final int idx) {
        return (idx << 1) + 2;
    }

    /**
     * Verifica si el índice se encuentra en el rango del arreglo.
     * Este método se emplea previamente a cualquier función que accede a
     * los índices de una colección.
     *
     * @param idx índice a verificar
     * @return si el índice está en el rango
     */
    private boolean checkRange(final int idx) {
        return idx >= 0 && idx < size;
    }

    /**
     * Aumenta la capacidad del arreglo, multiplica su capacidad 1.5 veces.
     */
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void grow() {
        final int newCap = size + (size >> 1);
        System.arraycopy(queue, 0, queue = (E[]) new Comparable[newCap], 0, size);

        capacity = newCap;
    }

    /**
     * Intercambia el contenido de los índices dados.
     *
     * @param idx1 Primerr índice del contenido a intercambiar
     * @param idx2 Segundo índice del contenido a intercambiar
     */
    private void swap(final int idx1, final int idx2) {
        final E tmpElem = queue[idx1];
        queue[idx1] = queue[idx2];
        queue[idx2] = tmpElem;
    }
}