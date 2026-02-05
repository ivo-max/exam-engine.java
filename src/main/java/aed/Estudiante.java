package aed;

//  -------- [ COMIENZO DE LA CLASE ESTUDIANTE ] --------

public class Estudiante implements Comparable<Estudiante> {

    private int[] examen;
    private boolean entrega;
    private int id;
    private boolean copion;
    private int resCorrectas;

    //  -------- [ CONSTRUCTOR DE LA CLASE ESTUDIANTE ] --------
    
    public Estudiante(int ID, int cantPreguntas) {
        examen = new int[cantPreguntas];
        for (int i = 0; i < cantPreguntas; i++) { 
            examen[i] = -1;
        }
        entrega = false;
        id = ID;
        copion = false;
        resCorrectas = 0;
    }

    // -------- [ GETTERS DE LOS ATRIBUTOS PRIVADOS DE UN ESTUDIANTE ] --------

    public int[] getExamen() {
        return this.examen;
    }

    public int getId() {
        return this.id;
    }

    public boolean getCopion() {
        return this.copion;
    }

    // -------- [ FIN DE LOS GETTERS ] --------

    // -------- [ SETTERS DE LOS ATRIBUTOS PRIVADOS DE UN ESTUDIANTE ] --------

    public void setEntrega(boolean nuevoValor) {
        this.entrega = nuevoValor;
    }

    public void setCopion(boolean nuevoValor) {
        this.copion = nuevoValor;
    }

    // -------- [ FIN SETTERS ] --------

    // -------- [ METODOS DE LA CLASE ESTUDIANTE ] --------

        // Método Nota (calcula la nota como lo pide el enunciado) 

        public double nota(int[] examenCanonico) {
            return ((double) resCorrectas * 100) / examenCanonico.length;
        }

        // Método compareTo (sirve para organizar el Heap segun prioridades, que son la siguientes con el orden respectivo:
        // 1. Aquellos que no entregaron
        // 2. Aquellos con menor cantidad de respuestas correctas
        // 3. Aquellos con menor ID

        @Override
        public int compareTo(Estudiante est) {
            if (entrega != est.entrega) {
                if (entrega == false) {
                    return -1;
                } 
                else {
                    return 1;
                }
            }
            if (this.resCorrectas > est.resCorrectas) {
                return 1;
            }
            if (this.resCorrectas < est.resCorrectas) {
                return -1;
            }
            if (id < est.id) {
                return -1;
            }
            if (this.id > est.id) {
                return 1; 
            }
            else {
                return 0; 
            }
        }
        //  Método resolverE (modifica el examen del estudiante y recalcula la cantidad de respuestas correctas que es yb atributo Estudiante)

        public void resolverE(Integer numEjer, Integer res, Integer resCorrecta) {
            int resAnterior = examen[numEjer];
            examen[numEjer] = res;

            if (resAnterior == resCorrecta && resAnterior != res) {
                resCorrectas -= 1;
            }
            if (res == resCorrecta && resAnterior != res) {
                resCorrectas += 1;
            }
        }

        // Método cambiarTodoExamen (reemplaza respuesta por respuesta de un examen teniendo en cuenta las de un examen que es pasado en parametro)
        
        public void cambiarTodoExamen(int[] examenNuevo, int[] examenCanonico) {
            resCorrectas = 0;
            for (int j = 0; j < examen.length; j++) {
                examen[j] = examenNuevo[j];
            }
            for (int i = 0; i < examen.length; i++) {
                if (examen[i] == examenCanonico[i]) {
                    resCorrectas += 1;
                }
            }
        }
}