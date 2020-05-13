/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

import EstructuraArbolB.lugar;
import EstructuraArbolB.ruta;
import estructuraGrafo.NodoGrafo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jhonny
 */
public class AnalizarArchivo {

    String txtAnalized;
    private final ArrayList<ruta> ListadoRutas;
    private final ArrayList<lugar> Nodos;
    private final ArrayList<String> list;
    private final ArrayList<NodoGrafo> NodosGrafo;
   
    
    public ArrayList<lugar>getNodo() {
        
        return Nodos;
    }
    public ArrayList<NodoGrafo> getNodosGrafo() {
        return NodosGrafo;
    }

    public AnalizarArchivo(String path) {
        analizate(path);
        ListadoRutas = new ArrayList();
        Nodos = new ArrayList();
        list = new ArrayList();
        NodosGrafo = new ArrayList();
       
    }

    private void analizate(String path) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fr);
            String linea = "";
            txtAnalized = "";
            while ((linea = br.readLine()) != null) {
                txtAnalized += linea ;
            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public boolean crearArregrlo() {
        String array[] = txtAnalized.split("|");
        int contador = 0, noNodo = 0;
        String Dato = "";
        String origen = "", destino = "";
        double timeVehiculo = 0, timePie = 0, gas = 0, desgaste = 0;
        for (String array1 : array) {
            switch (array1) {
                case "<":
                    break;
                case ">":
                    switch (contador) {
                        case 0:
                            origen = Dato;
                            break;
                        case 1:
                            destino = Dato;
                            break;
                        case 2:
                            try {
                                timeVehiculo = Integer.parseInt(Dato);
                            } catch (NumberFormatException e) {
                                return false;

                            }
                            break;
                        case 3:
                            try {
                                timePie = Integer.parseInt(Dato);
                            } catch (NumberFormatException e) {
                                return false;

                            }
                            break;
                        case 4:
                            try {
                                gas = Integer.parseInt(Dato);
                            } catch (NumberFormatException e) {
                                return false;

                            }
                            break;
                        case 5:
                            try {
                                desgaste = Integer.parseInt(Dato);
                            } catch (NumberFormatException e) {
                                return false;

                            }
                            break;

                    }
                    Dato = "";
                    contador++;
                    break;
                case "|":
                    break;
                default:
                    Dato += array1;
                    break;

            }
            if (contador == 6) {
                
                if (exists(origen)) {
                    Nodos.add(new lugar(origen, noNodo));
                    noNodo++;
                    System.out.println("agregando"+origen);
                    NodosGrafo.add(new NodoGrafo(origen));
                }
                if (exists(destino)) {
                    Nodos.add(new lugar(destino, noNodo));
                    noNodo++;
                    System.out.println("agregando"+destino);
                    NodosGrafo.add(new NodoGrafo(destino));
                }
                list.add(origen);
                list.add(destino);
                try {
                    var rta = new ruta(get(origen), get(destino), timeVehiculo, timePie, gas, desgaste);
                    NodosGrafo.get(rta.getOrigen().NoNodo).getHref().add(NodosGrafo.get(rta.getDestino().NoNodo));
                    NodosGrafo.get(rta.getOrigen().NoNodo).getRuta().add(rta);
                    
                } catch (NullPointerException e) {
                    return false;
                }

                contador = 0;
            }
        }
        return true;
    }

    private boolean exists(String dato) {
        for (int i = 0; i < list.size(); i++) {
            String data = list.get(i);
            if (dato.equals(data)) {
                return false;
            }
        }
        return true;
    }

    private lugar get(String dato) {
        for (int i = 0; i < list.size(); i++) {
            if (Nodos.get(i).name.equals(dato)) {
                return Nodos.get(i);
            }
        }
        return null;
    }

    public ArrayList<ruta> getListadoRutas() {
        return ListadoRutas;
    }

    public ArrayList<lugar> getNodos() {
        return Nodos;
    }

}
