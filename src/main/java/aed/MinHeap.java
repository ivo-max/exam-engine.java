package aed;

import java.util.ArrayList;


// -------- [ COMIENZO DE LA CLASE MIN HEAP ] -------

public class MinHeap<T extends Comparable<T>> {

    private ArrayList<HandleMinHeap> array;
    private int cantEst;

    // -------- [ COMIENZO DE LA CLASE HANDLE MIN HEAP ] --------
    public class HandleMinHeap implements Handle<T> {
        
        private T elem;
        private int posicion;

        //  -------- [ CONTRUCTOR DEL HANDLE ] --------
        private HandleMinHeap(T est, int pos) { //es privado porque la hanlde la crear el insertar
            this.posicion = pos;
            this.elem = est;    
        }

        public T valor() {
            return elem; 
        }

        public void actualizar() {
            if (posicion >= 0 && posicion < cantEst) {
                reordenar(posicion);
            }
        } 
    }
    // -------- [ FIN DE LA CLASE HANDLE MIN HEAP ] --------

    // -------- [ CONSTRUCTOR DEL MIN HEAP ] --------
    public MinHeap() {
        this.array = new ArrayList<HandleMinHeap>();
        cantEst = 0;
    }

    // Método longitud (da la cantidad de elementos del Heap)
    public int longitud() { 
        return cantEst;
    }

    public HandleMinHeap obtenerHandle(int i) {//no es privado, como esat en als correciones
        if (0 <= i && i< cantEst) {
            return array.get(i);
        }
        return null;
    }   

    // Método minimo (da el estudiante que se encuentra al principio del Heap, pero muestra su valor no lo elimina del Heap)
    public T minimo() {
        if (cantEst == 0) {
            return null;
        }
        return array.get(0).elem;
    }

    // Método intercambiar (toma dos indices y los intercambia) VER SI AL INTERCAMBIAR LA POSICION ACTUALIZA EL HANDLE.
    private void intercambiar(int indiceA, int indiceB) {
        HandleMinHeap A = array.get(indiceA);
        array.set(indiceA, array.get(indiceB));
        array.set(indiceB, A);

        if (array.get(indiceA) != null) {
            array.get(indiceA).posicion = indiceA;
        }

        if (array.get(indiceB) != null) {
            array.get(indiceB).posicion = indiceB;
        }

        
    }

    public HandleMinHeap insertar(T e) { // Complejidad Total: Es la misma que reordenar
        HandleMinHeap handle = new HandleMinHeap(e, cantEst);  
        array.add(handle);
        int pos = cantEst;
        cantEst += 1;
        reordenar(pos);
        return handle;
    }

    
    // Método insertarRapido (inserta pero sin reordenar) 
    public HandleMinHeap insertarRapido(T e) {
        HandleMinHeap han = new HandleMinHeap(e, cantEst);
        array.add(han);
        cantEst += 1;

        return han;
    }

    public void minHeapify() {//aca nos dijo que deberia ser privado
        int n = cantEst;
        for (int i = (n / 2) - 1; i >= 0; i--) {
            int indice = i;
            while (true) {
                int indiceHijoIzquierdo = 2 * indice + 1;
                int indiceHijoDerecho = 2 * indice + 2;
                int indiceMasChico = indice;

                if (indiceHijoIzquierdo < n 
                    && array.get(indiceHijoIzquierdo).elem.compareTo(array.get(indiceMasChico).elem) < 0) {
                    indiceMasChico = indiceHijoIzquierdo;
                }
           
                if (indiceHijoDerecho < n 
                    && array.get(indiceHijoDerecho).elem.compareTo(array.get(indiceMasChico).elem) < 0) {
                    indiceMasChico = indiceHijoDerecho;
                }

                if (indiceMasChico != indice) {
                    intercambiar(indice, indiceMasChico);
                    indice = indiceMasChico;
                } else {
                    break;
                }
            }
        }
    }

    // Método reordenar (Calculo con el compareTo como reorganizar el Heap para que siga cumpliendo su invariante)
    private void reordenar(int pos) { 
        int padre = (pos - 1) / 2;
        while (pos > 0 && array.get(pos).elem.compareTo(array.get(padre).elem) < 0) {
            intercambiar(pos, padre);
            pos = padre;
            padre = (pos - 1) / 2;
        }
        while (true) {
            int menor = pos;
            int hijoIzq = 2 * pos + 1;
            int hijoDer = 2 * pos + 2;
            if (hijoIzq < cantEst && array.get(hijoIzq).elem.compareTo(array.get(menor).elem) < 0) {
                menor = hijoIzq;
            }
            if (hijoDer < cantEst && array.get(hijoDer).elem.compareTo(array.get(menor).elem) < 0) {
                menor = hijoDer;
            }
            if (menor == pos) {
                break;
            }
            intercambiar(pos, menor);
            pos = menor;
        }
    }

    // Método eliminarMinimo (Toma la "raiz" del Heap y lo elimina y luego reordena el Heap)
    public void eliminarMinimo() {
        if (cantEst == 0) {
            return;
        }
        if (array.get(0) != null) {
            array.get(0).posicion = -1;
        }
        array.set(0, array.get(cantEst - 1));
        array.remove(cantEst - 1);
        cantEst -= 1;
        if (cantEst > 0) {
            array.get(0).posicion = 0;    
            reordenar(0);
        }
    }
}