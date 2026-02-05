package aed;

public class NotaFinal implements Comparable<NotaFinal> {
    public double nota;
    public int id;

    public NotaFinal(double NOTA, int ID){
        nota = NOTA;
        id = ID;
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null){
            return false;
        }
        if (otro.getClass() != this.getClass()){
            return false;
        }
        NotaFinal otraNotaFinal = (NotaFinal) otro; 

        if (this.nota != otraNotaFinal.nota || this.id != otraNotaFinal.id){
            return false;
        }
        return true;
    }

    @Override
    public int compareTo (NotaFinal n) {

        if ((int) this.nota > (int) n.nota) {
            return 1;
        }
        else if ((int) this.nota < (int) n.nota) {
            return -1;
        } 
        else if(this.id > n.id){
            return 1;
        } 
        else {
            return -1;
        }
    }

}
