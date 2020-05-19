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
import Main.ReportsController;
import estructuraGrafo.NodoGrafo;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class controlDecamino {

    ArbolB bThre;
    GridPane panel;
    ComboBox comboBox;
    Label o, d;
    TextArea rutas;
    int posO, posD;
    ArrayList<NodoGrafo> listado;
    private String anterior;

    public controlDecamino(ArrayList<NodoGrafo> nodosGrafo) {
        this.listado = nodosGrafo;
    }
    String data;
    Button btn;
    boolean isApie;
    ReportsController rpt;

    public void crearContent() {
        rpt = new ReportsController();
        this.posO = -1;

        panel = new GridPane();
        panel.setVgap(10);
        panel.setHgap(10);
        o = new Label();
        d = new Label();
        rutas = new TextArea("....");

        comboBox = new ComboBox();
        panel.add(new Label("Partida de inicio:"), 0, 0);
        panel.add(new Label("Partida de inicio:"), 1, 0);
        panel.add(o, 0, 1);
        panel.add(d, 1, 1);
        panel.add(new Label("Seleccione la ruta donde esta"), 0, 2);
        btn = new Button("Seleccionar ubicacion");
        panel.add(comboBox, 1, 2);
        panel.add(btn, 2, 2);
        panel.add(rutas, 0, 3);

        btn.setOnAction((t) -> {
            data = comboBox.getSelectionModel().getSelectedItem().toString();
            if (!data.isBlank()) {
                rpt.peor(getPos(data), new ArrayList<>(), new ArrayList(), -1, 0, true);
                
                String tmp = comboBox.getSelectionModel().getSelectedItem().toString();
                anterior=tmp;
                String caminos = rpt.getCaminos();
                agregarItems(data, rpt.getDatos());
                rpt.setCaminos("");
                rpt.setDatos();
                this.rutas.setText("Posibles caminos:\n" + caminos);
            }
        });
        getContenido().getChildren().add(panel);
        comboBox.setDisable(true);
        btn.setDisable(true);
        this.rutas.setEditable(false);

    }
    String auxAnt;
    public void modificar(String origen, String destino, int posO, int posD, boolean is) {
        this.isApie = is;
        this.anterior = "";
        this.auxAnt=origen;
        this.bThre = new ArbolB();
        this.posD = posD;
        this.posO = posO;
        comboBox.setDisable(false);
        btn.setDisable(false);
        o.setText(origen);
        d.setText(destino);

        int clave = getPos(origen);
        int key = getPos(destino);
        rpt.inicializar(clave, key, 0, listado);
        rpt.peor(clave, new ArrayList<>(), new ArrayList(), -1, 0, true);
        String caminos = rpt.getCaminos();
        agregarItems(origen, rpt.getDatos());
        rpt.setCaminos("");
        rpt.setDatos();
        this.rutas.setText("Posibles caminos:\n" + caminos);
    }
    int pos = 0;

    public void agregarItems(String dato, ArrayList<String> values) {
        boolean is = true;
        int valor = 0;
        if (!dato.equals(d.getText())) {
            ObservableList<String> list = comboBox.getItems();
            /*list.stream().map((list1) -> getPos(list1)).forEachOrdered((posv) -> {
                bThre.eliminar(posv);
            });*/
            String fd="";
            try{
            fd=comboBox.getSelectionModel().getSelectedItem().toString();
            }catch(NullPointerException e){}
            for (int i = 0; i < list.size(); i++) {
                if(!fd.equals(list.get(i))){
                    int posv=getPos(fd);
                    bThre.eliminar(posv);
                }
            }
            
            comboBox.getItems().clear();
            if(!anterior.isEmpty()){
            valor = getPos(anterior);
            }
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                if (value.equals(anterior) && isApie) {
                    is = false;
                } 
                this.comboBox.getItems().add(value);
                int clave = getPos(value);
                bThre.insertarDato(new ruta(clave, value));
            }

            if (isApie) {
                if (is) {
                    if (!anterior.isEmpty()) {
                        this.comboBox.getItems().add(anterior);
                        bThre.insertarDato(new ruta(valor, anterior));
                    }
                }
            }
            bThre.imprimir();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Has llegado a tu destino!");

            alert.showAndWait();
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
