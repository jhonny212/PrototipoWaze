/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author jhonny
 */
import static Main.Main.getContenido;
public class controlDecamino {
    public void crearContent(){
        GridPane panel=new GridPane();
        panel.setVgap(10);
        panel.setHgap(10);
        ComboBox comboBox = new ComboBox();
        panel.add(new Label("Seleccione la ruta donde esta"), 0, 0);
        panel.add(comboBox, 1, 0);
        getContenido().getChildren().add(panel);
        
    }
}
