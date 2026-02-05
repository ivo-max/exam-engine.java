package aed;

import java.util.ArrayList;

public class Edr {
    private int[] examenCanonico;
    private ArrayList<MinHeap<Estudiante>.HandleMinHeap> arrayHandles;
    private int dimension;
    private MinHeap<Estudiante> colaDePrioridad;


    // -------- [ CONSTRUCTOR EdR ] --------

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {
        this.examenCanonico = ExamenCanonico;
        this.dimension = LadoAula;
        this.arrayHandles = new ArrayList<>();
        this.colaDePrioridad = new MinHeap<>();
        for (int i = 0; i < Cant_estudiantes; i++) {
            Estudiante e = new Estudiante(i, ExamenCanonico.length);
            colaDePrioridad.insertarRapido(e); 
        }   

        // FLOYD
        colaDePrioridad.minHeapify();

        for (int i = 0; i < Cant_estudiantes; i++) {
            arrayHandles.add(null);
        }
//lo que habia qeu haber hecho era poner estos mfor en el minheap
//y que ya al llamar al contructor del min heap con los parametros quede todo ordenado
//y el array de hanldes incluido
        for (int i = 0; i < Cant_estudiantes; i++) {
            MinHeap<Estudiante>.HandleMinHeap handle = colaDePrioridad.obtenerHandle(i);
            Estudiante est = handle.valor();
            arrayHandles.set(est.getId(),handle);
        }
    }

    // --------------------------------------------------- [ NOTAS ] ---------------------------------------------------------------------------

    public double[] notas() {
        double[] listaNotas = new double[arrayHandles.size()]; 

        for (int k = 0; k < arrayHandles.size(); k++) { 
            listaNotas[k] = arrayHandles.get(k).valor().nota(examenCanonico);
        }
        return listaNotas;
    }

    // ------------------------------------------------ [ COPIARSE ] ------------------------------------------------------------------------

    private ArrayList<Integer> vecinos(int id) {
        double EstPorFila = Math.ceil(dimension / 2.0);
        ArrayList<Integer> vecinos = new ArrayList<>();
        
        if (id + EstPorFila < arrayHandles.size()) {
            vecinos.add(id + (int) EstPorFila);
        }
        if (id % EstPorFila == 0 && id != arrayHandles.size() - 1) {
            vecinos.add(id + 1);

        } 
        else if (id % EstPorFila == EstPorFila - 1) {
            vecinos.add(id - 1);
        } 
        else {
            if (id != arrayHandles.size() - 1) {
                vecinos.add(id + 1);
            }

            if (id > 0 && id % EstPorFila != 0) {
                vecinos.add(id - 1);
            }
        }
        return vecinos;
    }

    private ArrayList<Integer> vecinoYPregunta(int id) {
        ArrayList<Integer> vecinos = vecinos(id);
        Integer contador = 0;
        Integer preguntaACopiar = -1;
        Integer vecinoElegido = -1;

        for (int i = 0; i < vecinos.size(); i++) {
            Integer contadorAux = 0;
            Integer preguntaACopiarAux = -1;
            for (int j = 0; j < examenCanonico.length; j++) {
                if (arrayHandles.get(vecinos.get(i)).valor().getExamen()[j] != -1 &&
                        arrayHandles.get(id).valor().getExamen()[j] == -1) {
                    contadorAux += 1;
                    if (contadorAux == 1) {
                        preguntaACopiarAux = j;
                    }
                }
            }
            if (contadorAux > contador) {
                contador = contadorAux;
                preguntaACopiar = preguntaACopiarAux;
                vecinoElegido = vecinos.get(i);
            }

        }
        ArrayList<Integer> res = new ArrayList<>();
        res.add(vecinoElegido);
        res.add(preguntaACopiar);
        return res;
    }

    public void copiarse(int e) {
        ArrayList<Integer> lista = vecinoYPregunta(e); 
        Integer vecinoId = lista.get(0);
        Integer consigna = lista.get(1);
        Integer respuesta = arrayHandles.get(vecinoId).valor().getExamen()[consigna];
        arrayHandles.get(e).valor().resolverE(consigna, respuesta, examenCanonico[consigna]);
        arrayHandles.get(e).actualizar(); 
    }

    // ----------------------------------------------- [ RESOLVER ] ----------------------------------------------------------------

    public void resolver(int e, int NroEjercicio, int res) {
        MinHeap<Estudiante>.HandleMinHeap handle = arrayHandles.get(e);
        Estudiante estudiante = handle.valor();
        estudiante.resolverE(NroEjercicio, res, examenCanonico[NroEjercicio]);
        handle.actualizar();
    }

    // ------------------------------------------------ [ CONSULTAR DARK WEB ] -------------------------------------------------------


    public void consultarDarkWeb(int n, int[] examenDW) {
        ArrayList<Estudiante> nPeores = new ArrayList<>();

        for (int i = 0; i < n; i++) { 
            Estudiante minimo = colaDePrioridad.minimo(); 
            nPeores.add(minimo); 
            colaDePrioridad.eliminarMinimo(); 
        }
        

        for (int j = 0; j < nPeores.size(); j++) { 
            nPeores.get(j).cambiarTodoExamen(examenDW, examenCanonico); 
        }

        for (int x = 0; x < n; x++) {
            Estudiante estudianteCopiado = nPeores.get(x);
            MinHeap<Estudiante>.HandleMinHeap handle = colaDePrioridad.insertar(estudianteCopiado); 
            arrayHandles.set(estudianteCopiado.getId(), handle);
        }
    }

    // ------------------------------------------------- [ ENTREGAR ] -------------------------------------------------------------

    public void entregar(int estudiante) {
        arrayHandles.get(estudiante).valor().setEntrega(true);
        arrayHandles.get(estudiante).actualizar();
    }

    // ------------------------------------------------------- [ CHEQUEAR COPIAS ] -------------------------------------------------

    public int[] chequearCopias() {
        int[][] matriz = new int[examenCanonico.length][10];
        ArrayList<Estudiante> res = new ArrayList<>();
        for (int estudiante = 0; estudiante < arrayHandles.size();estudiante++) {
            int[] examen = arrayHandles.get(estudiante).valor().getExamen();
            for (int preg = 0; preg < examenCanonico.length; preg++) {
                if (examen[preg] != -1) {
                    matriz[preg][examen[preg]] += 1;
                }
            }
        }

        for (int i = 0; i < arrayHandles.size(); i++) {
            if (posibleCopion(arrayHandles.get(i).valor(), matriz)) {
                arrayHandles.get(i).valor().setCopion(true);
                res.add(arrayHandles.get(i).valor());
            }
        }
        return fromALtoArray(res);
    }

    private int[] fromALtoArray(ArrayList<Estudiante> array) {
        int[] res = new int[array.size()];
        for (int i = 0; i < array.size(); i++) {
            res[i] = array.get(i).getId();
        }
        return res;
    }

    private boolean posibleCopion(Estudiante e, int[][] matriz) {
        if (!examenVacio(e.getExamen())) {
            for (int j = 0; j < examenCanonico.length; j++) {
                if (e.getExamen()[j] != -1) {
                    int respuesta = e.getExamen()[j];
                    int cantidadRespuestasPorPreg =  matriz[j][respuesta];
                    double proporcion = (double) (cantidadRespuestasPorPreg - 1) / (arrayHandles.size() - 1);
                    if (proporcion< 0.25) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean examenVacio(int[] examen) {
        for (int j = 0; j < examen.length; j++) {
            if (examen[j] != -1) {
                return false;
            }
        }
        return true;
    }

    // -----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() {
        ArrayList<NotaFinal> listaNotas = new ArrayList<>();
        for (int i = 0; i < arrayHandles.size(); i++) {
            Estudiante est = arrayHandles.get(i).valor();
            if (!est.getCopion()) {
                listaNotas.add(new NotaFinal(est.nota(examenCanonico), est.getId()));
            }
        }
        NotaFinal[] res = fromALtoArrayNota(listaNotas);
        mergeSort(res, 0, res.length - 1);
    
        return res;
    }

    private void mergeSort(NotaFinal[] arr, int izq, int der) {
        if (izq < der) {
            int medio = (izq + der) / 2;
            mergeSort(arr, izq, medio);
            mergeSort(arr, medio + 1, der);
            merge(arr, izq, medio, der);
        }
    }

    private void merge(NotaFinal[] array, int izquierda, int medio, int derecha) {
        int n1 = medio - izquierda + 1;
        int n2 = derecha - medio;
    
        NotaFinal[] listaIzq = new NotaFinal[n1];
        NotaFinal[] listaDer = new NotaFinal[n2];

        for (int i = 0; i < n1; i++) {
            listaIzq[i] = array[izquierda + i];
        }
        for (int j = 0; j < n2; j++) {
            listaDer[j] = array[medio + 1 + j];
        }
        int indiceIzq = 0;
        int indiceDer = 0;
        int indiceNuevo = izquierda;
        while (indiceIzq < n1 && indiceDer < n2) {
            if (listaIzq[indiceIzq].compareTo(listaDer[indiceDer]) > 0) {
                array[indiceNuevo] = listaIzq[indiceIzq];
                indiceIzq++;
            } 
            else {
                array[indiceNuevo] = listaDer[indiceDer];
                indiceDer++;
            }
            indiceNuevo++;
        }
        while (indiceIzq < n1) {
            array[indiceNuevo] = listaIzq[indiceIzq];
            indiceIzq++;
            indiceNuevo++;
        }
        while (indiceDer < n2) {
            array[indiceNuevo] = listaDer[indiceDer];
            indiceDer++;
            indiceNuevo++;
        }
    }
    
    private NotaFinal[] fromALtoArrayNota(ArrayList<NotaFinal> array) {
        NotaFinal[] res = new NotaFinal[array.size()];
        for (int i = 0; i < array.size(); i++) {
            res[i] = array.get(i);
        }
        return res;
    }

    // Este es metodo para los tests propios.

    public int[] examenDe(int id) {
        return arrayHandles.get(id).valor().getExamen();
    }

}