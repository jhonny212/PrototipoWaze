package EstructuraArbolB;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ArbolB {

    static NodoB raiz;
    static int contador;
    public static boolean valid;
    public static int cnt;
    public ArbolB() {
        contador = 1;
        valid=false;
        cnt=0;
        this.raiz = new NodoB(contador);
    }

    public void insertarDato(ruta rta) {
        contador++;
        raiz.buscarNodo(raiz, rta);
        if (raiz.padre != null) {
            raiz = raiz.padre;
        }

    }

    public void imprimir() {
        if (raiz != null) {
            String graphviz = "digraph G {\n"
                    + "node [shape=record];\n";
            graphviz += printThreeB(raiz);
            graphviz += "}";
            FileWriter fichero = null;
            PrintWriter pw = null;
            try {
                fichero = new FileWriter("grafica.txt");
                pw = new PrintWriter(fichero);

                pw.println(graphviz);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Nuevamente aprovechamos el finally para
                    // asegurarnos que se cierra el fichero.
                    if (null != fichero) {
                        System.out.println("Se cerro");
                        fichero.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                String cmd = "dot -Tpng grafica.txt -o diagra.png"; //Comando de apagado en linux
                Runtime.getRuntime().exec(cmd);
                

            } catch (IOException ioe) {
                System.out.println(ioe);
            }
           
        }

    }

    private String printThreeB(NodoB aux) {
        if (aux == null) {
            return "";
        }
        String msj = "struct" + aux.NoNodo;
        String tmp1 = "", tmp2 = "", tmp3 = "";
        for (int i = 0; i < aux.size; i++) {
            try {
                Nodo tmp = aux.datos[i];
                if (tmp != null) {
                    if (i == 0) {
                        msj += " [label=\"<f" + tmp.key + "> " + tmp.getDato().getName();
                    } else {
                        msj += "| <f" + tmp.key + "> " + tmp.getDato().getName();
                    }
                    if (tmp.hijoIzq != null) {
                        int cod = i + 1;
                        tmp3 += "struct" + aux.NoNodo + ":f" + cod + " -> struct" + tmp.hijoIzq.NoNodo + ":f" + 1 + "; \n";
                        tmp1 += printThreeB(tmp.hijoIzq);

                    }
                    if (tmp.hijoDer != null) {
                        if(i==aux.size-1){
                        int cod = i + 1;
                        tmp3 += "struct" + aux.NoNodo + ":f" + cod + " -> struct" + tmp.hijoDer.NoNodo + ":f" + 1 + "; \n";
                        tmp2 += printThreeB(tmp.hijoDer);
                   
                        }
                    }

                } else {
                    break;
                }
            } catch (NullPointerException e) {

            }

        }

        return msj += "\"]; \n " + tmp1 + tmp2 + tmp3;
    }

    public static void eliminar(double rta){
    
    raiz.eliminar(raiz,rta,0);
    }

}
