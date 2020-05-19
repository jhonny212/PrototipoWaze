/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ClassesForInterfaz.tabla;
import EstructuraArbolB.ruta;
import estructuraGrafo.TablaTransiciones;
import estructuraGrafo.NodoGrafo;
import static estructuraGrafo.TablaTransiciones.getMejorDesgaste;
import static estructuraGrafo.TablaTransiciones.getMejorGas;
import static estructuraGrafo.TablaTransiciones.getMejorPie;
import static estructuraGrafo.TablaTransiciones.getMejorVehiculo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author jhonny
 */
public class ReportsController implements Initializable {

    private int x, y;

    NodoGrafo fin, origen;
    ArrayList<NodoGrafo> list;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    MenuItem m1, m2, m3, m4;
    @FXML
    Label title;
    @FXML
    ImageView img1, img2;
    @FXML
    private Label label1;
    @FXML
    private Label label2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void vehiculo(ActionEvent event) {
        total = 0.0;
        mejor(1);
        int pos = obtener(origen.getName());
        peor(pos, new ArrayList<>(), new ArrayList<>(), 1, 0.0, false);
        for (int i = 0; i < this.worst.size(); i++) {
            System.out.println(this.worst.get(i).getOrigen().name + "->" + this.worst.get(i).getDestino().name);

        }
        generarDiagramaPero(1);
    }

    @FXML
    private void desgaste(ActionEvent event) {
        total = 0.0;
        mejor(2);
        int pos = obtener(origen.getName());
        peor(pos, new ArrayList<>(), new ArrayList<>(), 2, 0.0, false);
        for (int i = 0; i < this.worst.size(); i++) {
            System.out.println(this.worst.get(i).getOrigen().name + "->" + this.worst.get(i).getDestino().name);

        }
        generarDiagramaPero(2);
    }

    @FXML
    private void gas(ActionEvent event) {
        total = 0.0;
        mejor(4);
        int pos = obtener(origen.getName());
        peor(pos, new ArrayList<>(), new ArrayList<>(), 4, 0.0, false);
        for (int i = 0; i < this.worst.size(); i++) {
            System.out.println(this.worst.get(i).getOrigen().name + "->" + this.worst.get(i).getDestino().name);

        }
        generarDiagramaPero(4);
    }

    @FXML
    private void aPie(ActionEvent event) {
        total = 0.0;
        mejor(3);
        int pos = obtener(origen.getName());
        peor(pos, new ArrayList<>(), new ArrayList<>(), 3, 0.0, false);
        for (int i = 0; i < this.worst.size(); i++) {
            System.out.println(this.worst.get(i).getOrigen().name + "->" + this.worst.get(i).getDestino().name);

        }
        generarDiagramaPero(3);
    }
    private double total;
    private ArrayList<ruta> worst;

    public void init() {
        worst = new ArrayList<>();
    }

    public void inicializar(int x, int y, int longitud, ArrayList<NodoGrafo> list) {
        this.x = x;
        this.y = y;
        this.fin = list.get(y);
        this.origen = list.get(x);
        this.list = list;
        worst = new ArrayList<>();
        total = 0.0;
        caminos = "";
        datos = new ArrayList<>();

    }

    private void mejor(int opc) {
        tabla[][] mejor = null;
        switch (opc) {
            case 1:
                title.setText("mejor vehiculo");
                mejor = getMejorVehiculo();
                break;
            case 2:
                title.setText("Mejor Camino para desgaste");
                mejor = getMejorDesgaste();
                break;
            case 3:
                title.setText("Mejor Camino para ir a pie ");
                mejor = getMejorPie();
                break;
            case 4:
                title.setText("Mejor Camino para el gas");
                mejor = getMejorGas();
                break;
        }

        final int columna = this.y;
        int fila = this.x;
        tabla tmp = mejor[fila][columna];
        String recorrido = "digraph G {\n";
        int count = 0;
        int totalDeCamino = 0;
        recorrido += "node" + fila + "[fillcolor=yellow, style=\"rounded,filled\", shape=circle, label=" + origen.getName() + "] \n";
        while (true) {
            count++;
            String letter = tmp.getVertice();
            int aux = fila;
            fila = obtener(letter);
            recorrido += "node" + fila + "[fillcolor=yellow, style=\"rounded,filled\", shape=circle, label=" + letter + "]\n ";
            if (count == 1) {

                totalDeCamino += tmp.getDato();
                recorrido += "node" + aux + "-> node" + fila + " [label=\"" + tmp.getDato() + "\"]\n";
            }

            if (letter.equals(fin.getName())) {
                break;
            } else {
                tmp = mejor[fila][columna];
                aux = fila;
                fila = obtener(tmp.getVertice());
                totalDeCamino += tmp.getDato();
                recorrido += "node" + aux + "-> node" + fila + " [label=\"" + tmp.getDato() + "\"]\n";

            }
        }
        recorrido += "}";

        this.label1.setText("recorrido:" + String.valueOf(totalDeCamino));
        TablaTransiciones tb = new TablaTransiciones();
        tb.generarGrafica(recorrido, "mejor", "mejor");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        File f = new File("mejor.png");
        Image source = null;
        try {
            source = new Image(new FileInputStream(f.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.img1.setImage(source);
    }

    public int obtener(String name) {
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getName().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    public void peor(int indice, ArrayList<String> p, ArrayList<ruta> rutas, int tipo, double tota, boolean valid) {
        NodoGrafo list1 = list.get(indice);
        String name = list1.getName();
        for (int i = 0; i < list1.getRuta().size(); i++) {
            ruta rta = list1.getRuta().get(i);
            String destino = rta.getDestino().name;
            ArrayList<String> pila = new ArrayList();
            ArrayList<ruta> rts = new ArrayList();
            double valor = 0.0;
            valor += tota;
            if (!valid) {
                for (int j = 0; j < rutas.size(); j++) {
                    rts.add(rutas.get(j));
                }
            }
            for (int j = 0; j < p.size(); j++) {
                pila.add(p.get(j));
            }
            if (!existe(name, pila)) {
                pila.add(name);
            }
            if (!existe(destino, pila)) {
                pila.add(destino);
                rts.add(rta);
                int cod = rta.getDestino().NoNodo;
                if (!valid) {
                    switch (tipo) {
                        case 1:
                            valor += rta.getTiempoVehiculo();
                            break;
                        case 2:
                            valor += rta.getDesgastePersona();
                            break;
                        case 3:
                            valor += rta.getTiempoPie();
                            break;
                        case 4:
                            valor += rta.getConsumoGas();
                            break;

                    }
                }
                if (destino.equals(fin.getName())) {
                    if (valor > total && (!valid)) {
                        total = valor;
                        this.worst = rts;
                    } else {
                        for (int j = 0; j < pila.size(); j++) {
                            if (j != 0) {
                                caminos += " Mover a ";
                            }
                            caminos += pila.get(j);

                        }
                        if (!existe(pila.get(1), datos)) {
                            datos.add(pila.get(1));
                        }
                        caminos += "\n";

                    }
                } else {
                    peor(cod, pila, rts, tipo, valor, valid);
                }
            }
        }
    }

    private String caminos;
    
    boolean existe(String name, ArrayList<String> pila) {
        for (int i = 0; i < pila.size(); i++) {
            if (name.equals(pila.get(i))) {
                return true;
            }

        }
        return false;
    }

    private void generarDiagramaPero(int param) {
        String graph = "digraph G {";
        double valor = 0.0;
        for (ruta worst1 : this.worst) {
            switch (param) {
                case 1:
                    valor = worst1.getTiempoVehiculo();
                    break;
                case 2:
                    valor = worst1.getDesgastePersona();
                    break;
                case 3:
                    valor = worst1.getTiempoPie();
                    break;
                case 4:
                    valor = worst1.getConsumoGas();
                    break;

            }
            graph += "node" + worst1.getOrigen().NoNodo + "[fillcolor=yellow, style=\"rounded,filled\", shape=circle, label=" + worst1.getOrigen().name + "] \n";
            graph += "node" + worst1.getOrigen().NoNodo + "-> node" + worst1.getDestino().NoNodo + " [label=\"" + valor + "\"]\n";

        }
        graph += "node" + this.worst.get(this.worst.size() - 1).getDestino().NoNodo + "[fillcolor=yellow, style=\"rounded,filled\", shape=circle, label=" + this.worst.get(this.worst.size() - 1).getDestino().name + "] \n";
        graph += "}";
        this.label2.setText("recorrido:" + String.valueOf(total));

        TablaTransiciones tb = new TablaTransiciones();
        tb.generarGrafica(graph, "peor", "peor");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        File f = new File("peor.png");
        Image source = null;
        try {
            source = new Image(new FileInputStream(f.getAbsolutePath()));
        } catch (FileNotFoundException e) {
        }
        this.img2.setImage(source);

    }

    public String getCaminos() {
        return caminos;
    }

    public void setCaminos(String caminos) {
        this.caminos = caminos;
    }
    private ArrayList<String> datos;

    public ArrayList<String> getDatos() {

        return datos;
    }

    public void setDatos() {
        this.datos = new ArrayList<>();
    }

}
