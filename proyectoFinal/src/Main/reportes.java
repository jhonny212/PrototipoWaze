/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ClassesForInterfaz.tabla;
import estructuraGrafo.NodoGrafo;
import estructuraGrafo.TablaTransiciones;
import static estructuraGrafo.TablaTransiciones.getMejorDesgaste;
import static estructuraGrafo.TablaTransiciones.getMejorGas;
import static estructuraGrafo.TablaTransiciones.getMejorPie;
import static estructuraGrafo.TablaTransiciones.getMejorVehiculo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;

/**
 *
 * @author jhonny
 */
public class reportes {

    private VBox panel;
    private final int x, y;
    Label title;
    Label cam;
    
    int longitud;
    NodoGrafo fin, origen;
    ImageView im;
    int contador;
    HBox box;
    ArrayList<NodoGrafo> list;

    public reportes(int x, int y, int longitud, ArrayList<NodoGrafo> list) {
        this.panel = new VBox(30);
        box = new HBox();
        this.x = x;
        this.y = y;
        this.fin = list.get(y);
        this.origen = list.get(x);
        generar();
        im = new ImageView();
        title = new Label();
        contador = 0;
        this.list = list;
        cam=new Label();
    }

    public VBox getPanel() {
        return panel;
    }

    private void generar() {
        Menu m = new Menu("Opciones");
        MenuItem m1 = new MenuItem("Mejor/peor Viaje vehiculo");
        MenuItem m2 = new MenuItem("Mejor/peor Viaje pie");
        MenuItem m3 = new MenuItem("Mejor/peor Viaje con gasolina");
        MenuItem m4 = new MenuItem("Mejor/peor Viaje con desgaste");

        m.getItems().addAll(m1, m2, m3, m4);
        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        panel.getChildren().add(mb);
        m1.setOnAction((t) -> {
            mejor(1);
        });
        m2.setOnAction((t) -> {
            mejor(3);
        });
        m3.setOnAction((t) -> {
            mejor(4);
        });
        m4.setOnAction((t) -> {
            mejor(2);
        });
        

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
        int totalDeCamino=0;
        recorrido += "node" + fila + "[fillcolor=yellow, style=\"rounded,filled\", shape=circle, label=" + origen.getName() + "] \n";
        while (true) {
            count++;
            String letter = tmp.getVertice();
            int aux=fila;
             fila=obtener(letter);
            recorrido += "node" + fila + "[fillcolor=yellow, style=\"rounded,filled\", shape=circle, label=" + letter + "]\n ";
            if (count == 1) {
               
               totalDeCamino+=tmp.getDato();
               recorrido += "node" + aux + "-> node" + fila + " [label=\"" + tmp.getDato() + "\"]\n";
            }
             
            
            if (letter.equals(fin.getName())) {
                break;
            } else {
                tmp = mejor[fila][columna];
                aux=fila;
                fila=obtener(tmp.getVertice());
                totalDeCamino+=tmp.getDato();
                recorrido += "node" + aux + "-> node" + fila + " [label=\"" + tmp.getDato() + "\"]\n";
                
            }
        }
        recorrido += "}";
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }
        this.cam.setText("Se tiene un recorrido de->"+String.valueOf(totalDeCamino));
        TablaTransiciones tb = new TablaTransiciones();
        tb.generarGrafica(recorrido, "mejor", "mejor");
        File f = new File("mejor.png");
        Image source = null;
        try {
            source = new Image(new FileInputStream(f.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.im.setImage(source);
        
        if (contador == 0) {
            box.getChildren().add(im);
            box.setAlignment(Pos.CENTER);
            title.setAlignment(Pos.CENTER_RIGHT);
            cam.setAlignment(Pos.CENTER_RIGHT);
            this.panel.getChildren().addAll(box, title,cam);
        }
        contador++;

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
