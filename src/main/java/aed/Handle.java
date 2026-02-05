package aed;

interface Handle<T> {
    /**
     * Devuelve el valor del elemento
     * 
     */
    T valor();

    /**
     * Actualiza la posicion
     */
    void actualizar();
}