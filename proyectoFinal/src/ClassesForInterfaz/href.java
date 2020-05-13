/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author jhonny
 */
public class href {

    private String inicio, fin, label;

    public href(String inicio, String fin) {
        this.inicio = inicio;
        this.fin = fin;
        generar();
    }

    public href() {
    }

    public String generarDiagrama(ArrayList<href> referencias, ArrayList<nodosForDiagram> nodos) {
        String diagrama = "digraph G {\n";
        for (int i = 0; i < nodos.size(); i++) {
            diagrama += nodos.get(i).getLabel() + "\n";
        }
        for (int i = 0; i < referencias.size(); i++) {
            diagrama += referencias.get(i).label;
        }
        diagrama += "}";
        generarGrafica(diagrama);
        return diagrama;
    }

    private void generar() {
        this.label = this.fin + "->" + this.inicio + ";\n";
    }

    private void generarGrafica(String dato) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("ruta.txt");
            pw = new PrintWriter(fichero);
            pw.println(dato);
          
        } catch (IOException e) {
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
            }
        }
        try {
            String cmd = "dot -Tpng ruta.txt -o ruta.png"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd);

        } catch (IOException ioe) {
        }
    }
}
