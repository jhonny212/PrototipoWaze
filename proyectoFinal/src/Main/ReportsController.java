/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ClassesForInterfaz.tabla;
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
    int longitud;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void vehiculo(ActionEvent event) {
        mejor(1);
    }

    @FXML
    private void desgaste(ActionEvent event) {
        mejor(2);
    }

    @FXML
    private void gas(ActionEvent event) {
        mejor(4);
    }

    @FXML
    private void aPie(ActionEvent event) {
        mejor(3);
    }

    public void inicializar(int x, int y, int longitud, ArrayList<NodoGrafo> list) {
        this.x = x;
        this.y = y;
        this.fin = list.get(y);
        this.origen = list.get(x);
        this.list = list;

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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }
        //this.cam.setText("Se tiene un recorrido de->"+String.valueOf(totalDeCamino));
        TablaTransiciones tb = new TablaTransiciones();
        tb.generarGrafica(recorrido, "mejor", "mejor");
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
}
