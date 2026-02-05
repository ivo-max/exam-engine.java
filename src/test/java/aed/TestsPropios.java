package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;

class TestsPropios {
    Edr edr;
    int d_aula;
    int cant_alumnos;
    int[] solucion;

    @BeforeEach
    void setUp() {
        d_aula = 5;
        cant_alumnos = 5;
        solucion = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        edr = new Edr(d_aula, cant_alumnos, solucion);
    }

    @Test
    void copiarse_de_vecinos_con_igual_cantidad_de_respuestas() {
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(1, 2, 2);
        edr.resolver(1, 3, 3);
        edr.resolver(2, 4, 4);
        edr.resolver(2, 5, 5);
        edr.resolver(3, 6, 6);
        edr.resolver(3, 7, 7);
        edr.resolver(4, 8, 8);
        edr.resolver(4, 9, 9);

        edr.copiarse(3);
        int[] nuevoExamen3 = edr.examenDe(3);
        int[] examen3 = new int[] { -1, -1, -1, -1, -1, -1, 6, 7, 8, -1 };
        assertTrue(Arrays.equals(nuevoExamen3, examen3));

        edr.copiarse(4);
        int[] nuevoExamen4 = edr.examenDe(4);
        int[] examen4 = new int[] { -1, -1, -1, -1, -1, -1, 6, -1, 8, 9 };
        assertTrue(Arrays.equals(nuevoExamen4, examen4));

        edr.copiarse(1);
        int[] nuevoExamen1 = edr.examenDe(1);
        int[] examen1 = new int[] { -1, -1, 2, 3, -1, -1, 6, -1, -1, -1 };
        assertTrue(Arrays.equals(nuevoExamen1, examen1));

        edr.copiarse(2);
        int[] nuevoExamen2 = edr.examenDe(2);
        int[] examen2 = new int[] { -1, -1, 2, -1, 4, 5, -1, -1, -1, -1 };
        assertTrue(Arrays.equals(nuevoExamen2, examen2));
    }

    @Test
    void consultar_dark_web_puntajes_empatados() {
        edr = new Edr(d_aula, cant_alumnos, solucion);
        int[] examenDarkWeb = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        edr.consultarDarkWeb(1, examenDarkWeb);
        int[] nuevoExamen0 = edr.examenDe(0);
        assertTrue(Arrays.equals(nuevoExamen0, examenDarkWeb));

        edr.resolver(2, 0, 0);
        edr.consultarDarkWeb(2, examenDarkWeb);
        int[] nuevoExamen1 = edr.examenDe(1);
        int[] nuevoExamen3 = edr.examenDe(3);

        assertTrue(Arrays.equals(nuevoExamen1, examenDarkWeb));

        assertTrue(Arrays.equals(nuevoExamen3, examenDarkWeb));
        int[] examen4 = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

        assertTrue(Arrays.equals(examen4, edr.examenDe(4)));
        int[] examen2 = { 0, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

        assertTrue(Arrays.equals(examen2, edr.examenDe(2)));
    }

    @Test
    void corregir_con_varios_copiones() {
        edr = new Edr(d_aula, cant_alumnos, solucion);
        edr.chequearCopias();
        NotaFinal[] notas = { new NotaFinal(0.0, 4),
                new NotaFinal(0.0, 3),
                new NotaFinal(0.0, 2),
                new NotaFinal(0.0, 1),
                new NotaFinal(0.0, 0) };
        assertTrue(Arrays.equals(notas, edr.corregir()));

        edr = new Edr(d_aula, cant_alumnos, solucion);
        int[] examenDarkWeb = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        edr.consultarDarkWeb(2, examenDarkWeb);
        edr.chequearCopias();
        NotaFinal[] notas1 = { new NotaFinal(0.0, 4),
                new NotaFinal(0.0, 3),
                new NotaFinal(0.0, 2) };
        assertTrue(Arrays.equals(notas1, edr.corregir()));

        edr = new Edr(d_aula, cant_alumnos, solucion);
        edr.consultarDarkWeb(2, examenDarkWeb);
        edr.resolver(3, 0, 0);
        edr.resolver(3, 1, 1);
        edr.resolver(3, 2, 2);
        edr.resolver(3, 3, 3);
        edr.resolver(3, 4, 4);
        edr.resolver(3, 5, 5);
        edr.resolver(3, 6, 6);
        edr.resolver(3, 7, 7);
        edr.resolver(3, 8, 8);
        edr.resolver(3, 9, 1);
        edr.chequearCopias();
        NotaFinal[] notas2 = { new NotaFinal(90.0, 3),
                new NotaFinal(0.0, 4),
                new NotaFinal(0.0, 2) };
        assertTrue(Arrays.equals(notas2, edr.corregir()));

        edr = new Edr(d_aula, cant_alumnos, solucion);
        edr.consultarDarkWeb(2, examenDarkWeb);
        edr.resolver(3, 0, 0);
        edr.resolver(3, 1, 1);
        edr.resolver(3, 2, 2);
        edr.resolver(3, 3, 3);
        edr.resolver(3, 4, 4);
        edr.resolver(3, 5, 5);
        edr.resolver(3, 6, 6);
        edr.resolver(3, 7, 7);
        edr.resolver(3, 8, 8);
        edr.resolver(3, 9, 9);
        edr.chequearCopias();
        NotaFinal[] notas3 = { new NotaFinal(0.0, 4),
                new NotaFinal(0.0, 2) };
        assertTrue(Arrays.equals(notas3, edr.corregir()));
    }

    @Test
    void consultar_dark_web_con_algunos_que_entregaron() {
        edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.entregar(0);

        int[] examenDarkWeb = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        edr.consultarDarkWeb(1, examenDarkWeb);

        int[] examen1 = edr.examenDe(1);
        assertTrue(Arrays.equals(examenDarkWeb, examen1));

        edr.entregar(1);

        edr.resolver(2, 0, 0);

        edr.consultarDarkWeb(2, examenDarkWeb);
        
        int[] examen3 = edr.examenDe(3);
        assertTrue(Arrays.equals(examenDarkWeb, examen3));
        int[] examen4 = edr.examenDe(4);
        assertTrue(Arrays.equals(examenDarkWeb, examen4));
    }
}
