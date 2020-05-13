/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author jhonny
 */
public class controlDevehiculo {

    public VBox panelDatos() {
        VBox box = new VBox(10);
        HBox datos = new HBox(10);
        datos.setAlignment(Pos.CENTER);
        TextField nombre=new TextField();
        ComboBox inicio=new ComboBox();
        ComboBox fin=new ComboBox();
        
        return box;
    }
}
