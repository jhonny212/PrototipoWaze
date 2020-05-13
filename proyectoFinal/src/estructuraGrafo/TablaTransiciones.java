package estructuraGrafo;

import ClassesForInterfaz.tabla;
import EstructuraArbolB.ruta;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

public class TablaTransiciones {

    private ArrayList<ruta> listaNodos;
    private static tabla mejorVehiculo[][], mejorPie[][], mejorGas[][], mejorDesgaste[][];
    private static tabla peorDesgaste[][], peorVehiculo[][], peorPie[][], peorGas[][];

    ArrayList<NodoGrafo> Nodos;
    Object[][] tabla;
    int x;
    private String path = "";

    public String getPath() {
        return path;
    }

    public TablaTransiciones() {
    }

    public TablaTransiciones(ArrayList<ruta> listaNodos, ArrayList<NodoGrafo> nodos) {
        this.listaNodos = listaNodos;
        this.Nodos = nodos;
        int x = this.Nodos.size();
        mejorVehiculo = new tabla[x][x];
        peorVehiculo = new tabla[x][x];
        mejorPie = new tabla[x][x];
        peorPie = new tabla[x][x];
        mejorGas = new tabla[x][x];
        peorGas = new tabla[x][x];
        mejorDesgaste = new tabla[x][x];
        peorDesgaste = new tabla[x][x];
        print();
    }

    private void print() {
        String grafica = "digraph G {\n";
        for (NodoGrafo Nodo : Nodos) {
            String origen = Nodo.name;
            grafica = Nodo.href.stream().map((href) -> origen + " -> " + href.name + ";\n").reduce(grafica, String::concat);
        }
        grafica += "}";
        for (int i = 0; i < Nodos.size(); i++) {
            for (int j = 0; j < Nodos.size(); j++) {
                String column = Nodos.get(j).name;
                boolean v = true;
                for (int k = 0; k < Nodos.get(i).ruta.size(); k++) {
                    if (column.equals(Nodos.get(i).ruta.get(k).getDestino().name)) {
                        double peso = Nodos.get(i).ruta.get(k).getTiempoVehiculo();
                        mejorVehiculo[i][j] = new tabla(peso, column, j);
                        peso = Nodos.get(i).ruta.get(k).getDesgastePersona();
                        mejorDesgaste[i][j] = new tabla(peso, column, j);
                        peso = Nodos.get(i).ruta.get(k).getConsumoGas();
                        mejorGas[i][j] = new tabla(peso, column, j);
                        peso = Nodos.get(i).ruta.get(k).getTiempoPie();
                        mejorPie[i][j] = new tabla(peso, column, j);
                        v = false;
                        break;
                    }
                }
                if (v) {
                    mejorVehiculo[i][j] = new tabla(-1, column, j);
                    mejorDesgaste[i][j] = new tabla(-1, column, j);
                    mejorGas[i][j] = new tabla(-1, column, j);
                    mejorPie[i][j] = new tabla(-1, column, j);

                }
            }
            mejorVehiculo[i][i] = new tabla(0, Nodos.get(i).name, i);
            mejorGas[i][i] = new tabla(0, Nodos.get(i).name, i);
            mejorDesgaste[i][i] = new tabla(0, Nodos.get(i).name, i);
            mejorPie[i][i] = new tabla(0, Nodos.get(i).name, i);
        }
        peorDesgaste = mejorDesgaste;
        peorVehiculo = mejorVehiculo;
        peorGas = mejorGas;
        peorPie = mejorPie;
        generarGrafica(grafica, "grafica", "diagram");
        File f = new File("diagram.png");
        path = f.getAbsolutePath();

        generarTablas();
    }

    public void generarGrafica(String dato, String nombretxt, String nombrepng) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombretxt + ".txt");
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
            String cmd = "dot -Tpng " + nombretxt + ".txt -o " + nombrepng + ".png"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd);

        } catch (IOException ioe) {
        }
    }

    private void generarTablas() {
        int x = this.Nodos.size();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (j != i) {
                    for (int k = 0; k < x; k++) {
                        if (k != i) {

                            tabla tb1 = mejorVehiculo[i][k];
                            tabla tb2 = mejorVehiculo[j][i];
                            double dato = mejorVehiculo[j][k].getDato();
                            double suma = tb1.getDato() + tb2.getDato();
                            //a pie
                            tabla tbb1 = mejorPie[i][k];
                            tabla tbb2 = mejorPie[j][i];
                            double dato2 = mejorPie[j][k].getDato();
                            double suma2 = tbb1.getDato() + tbb2.getDato();
                            //desgaste
                            tabla tbbb1 = mejorDesgaste[i][k];
                            tabla tbbb2 = mejorDesgaste[j][i];
                            double dato3 = mejorDesgaste[j][k].getDato();
                            double suma3 = tbbb1.getDato() + tbbb2.getDato();
                            //gasolina
                            tabla tbbbb1 = mejorGas[i][k];
                            tabla tbbbb2 = mejorGas[j][i];
                            double dato4 = mejorGas[j][k].getDato();
                            double suma4 = tbbbb1.getDato() + tbbbb2.getDato();
                            //vehiculo
                            modificarTabla(dato, suma, tb1, tb2, j, k, i, 1);
                            //para pie
                            modificarTabla(dato2, suma2, tbb1, tbb2, j, k, i, 2);
                            //desgaste
                            modificarTabla(dato3, suma3, tbbb1, tbbb2, j, k, i, 3);
                            //gasolina
                            modificarTabla(dato4, suma4, tbbbb1, tbbbb2, j, k, i, 4);
                        }
                    }
                }
            }
        }
       leer();

    }

    private void modificarTabla(double dato, double suma, tabla t1, tabla t2, int j, int k, int i, int opc) {
        if (dato == -1) {
            if (isInfinit(t1.getDato(), t2.getDato())) {
                switch (opc) {
                    case 1:
                        mejorVehiculo[j][k].setDato(suma);
                        mejorVehiculo[j][k].setVertice(mejorVehiculo[i][i].getVertice());

                        break;
                    case 2:
                        mejorPie[j][k].setDato(suma);
                        mejorPie[j][k].setVertice(mejorPie[i][i].getVertice());

                        break;
                    case 3:
                        mejorDesgaste[j][k].setDato(suma);
                        mejorDesgaste[j][k].setVertice(mejorDesgaste[i][i].getVertice());

                        break;
                    case 4:
                        mejorGas[j][k].setDato(suma);
                        mejorGas[j][k].setVertice(mejorGas[i][i].getVertice());
                        break;
                }

            }
        } else {

            if (isInfinit(t1.getDato(), t2.getDato())) {
                if (suma < dato) {
                    switch (opc) {
                        case 1:
                            System.out.println(t1.getDato() + " " + t2.getDato());
                            mejorVehiculo[j][k].setDato(suma);
                            mejorVehiculo[j][k].setVertice(mejorVehiculo[i][i].getVertice());
                            mejorVehiculo[j][k].setInt(i);

                            break;
                        case 2:
                            mejorPie[j][k].setDato(suma);
                            mejorPie[j][k].setVertice(mejorPie[i][i].getVertice());
                            mejorPie[j][k].setInt(i);

                            break;
                        case 3:
                            mejorDesgaste[j][k].setDato(suma);
                            mejorDesgaste[j][k].setVertice(mejorDesgaste[i][i].getVertice());
                            mejorDesgaste[j][k].setInt(i);
                            break;
                        case 4:
                            mejorGas[j][k].setDato(suma);
                            mejorGas[j][k].setVertice(mejorGas[i][i].getVertice());
                            mejorGas[j][k].setInt(i);
                            break;
                    }
                }
            }
        }

    }

    private void leer() {
        int x = this.Nodos.size();
        String datos = "", vertices = "";
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                datos += "[" + mejorPie[i][j].getDato() + "] ";
                vertices += "[" + mejorPie[i][j].getVertice() + "] ";
            }
            datos += "\n";
            vertices += "\n";
        }
        System.out.println(datos);
        System.out.println(vertices);

    }

    private boolean isInfinit(double d1, double d2) {
        if (d1 < 0) {
            return false;
        }
        if (d2 < 0) {
            return false;
        }

        return true;
    }

    public static tabla[][] getMejorVehiculo() {
        return mejorVehiculo;
    }

    public static tabla[][] getMejorPie() {
        return mejorPie;
    }

    public static tabla[][] getMejorGas() {
        return mejorGas;
    }

    public static tabla[][] getMejorDesgaste() {
        return mejorDesgaste;
    }

}
