/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

import EstructuraArbolB.ArbolB;
import EstructuraArbolB.ruta;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author jhonny
 */
import static Main.Main.getContenido;
import estructuraGrafo.NodoGrafo;
import java.util.ArrayList;
import javafx.scene.control.Button;

public class controlDecamino {

    ArbolB bThre;
    GridPane panel;
    ComboBox comboBox;
    Label o, d;
    int posO, posD;
    ArrayList<NodoGrafo> listado;

    public controlDecamino(ArrayList<NodoGrafo> nodosGrafo) {
        this.listado = nodosGrafo;
    }
    String data;
    Button btn;

    public void crearContent() {
        this.posO = -1;
        panel = new GridPane();
        panel.setVgap(10);
        panel.setHgap(10);
        o = new Label();
        d = new Label();
        comboBox = new ComboBox();
        panel.add(new Label("Partida de inicio:"), 0, 0);
        panel.add(new Label("Partida de inicio:"), 1, 0);
        panel.add(o, 0, 1);
        panel.add(d, 1, 1);
        panel.add(new Label("Seleccione la ruta donde esta"), 0, 2);
        btn = new Button("Seleccionar ubicacion");
        panel.add(comboBox, 1, 2);
        panel.add(btn, 2, 2);

        btn.setOnAction((t) -> {
            data = comboBox.getSelectionModel().getSelectedItem().toString();
            if (!data.isBlank()) {
                agregarItems(0, data);
            }
        });
        getContenido().getChildren().add(panel);
        comboBox.setDisable(true);
        btn.setDisable(true);

    }

    public void modificar(String origen, String destino, int posO, int posD) {
        this.bThre = new ArbolB();
        this.posD = posD;
        this.posO = posO;
        comboBox.setDisable(false);
        btn.setDisable(false);
        o = new Label(origen);
        d = new Label(destino);
        agregarItems(0, origen);
    }
    int pos = 0;

    public void agregarItems(int pos, String dato) {
        if (!dato.equals(d.getText())) {
            comboBox.getItems().clear();
            for (int i = 0; i < listado.size(); i++) {
                if (listado.get(i).getName().equals(dato)) {
                    for (int j = 0; j < listado.get(i).getHref().size(); j++) {
                        String value=listado.get(i).getHref().get(j).getName();
                        this.comboBox.getItems().add(value);
                        int clave=getPos(value);
                        bThre.insertarDato(new ruta(clave, value));
                    }
                }
            }
            bThre.imprimir();
        } else {
            System.out.println("Has llegado!!");
            comboBox.setDisable(true);
            btn.setDisable(true);
        }
    }

    int getPos(String name) {
        for (int i = 0; i < this.listado.size(); i++) {
            if (name.equals(listado.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

}
