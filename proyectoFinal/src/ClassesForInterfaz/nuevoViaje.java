/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

import EstructuraArbolB.lugar;
import estructuraGrafo.NodoGrafo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author jhonny
 */
public class nuevoViaje {

    boolean isSelected;
    String digram;

    public boolean isIsSelected() {
        return isSelected;
    }

    public String getDigram() {
        return digram;
    }

    public nuevoViaje() {

    }
  public  int x, y;

    public void init(ArrayList<NodoGrafo> nodos) {
        isSelected = false;
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Vehiculo",
                        "Pie"
                );
        ComboBox comboBox = new ComboBox(options);
        ComboBox Nodos = new ComboBox();
        ComboBox Fin = new ComboBox();

        for (int i = 0; i < nodos.size(); i++) {
            System.out.println(nodos.get(i).getName());
            Nodos.getItems().add(nodos.get(i).getName());
        }

        Nodos.setOnAction(e -> {
            x = Nodos.getSelectionModel().getSelectedIndex();
            String array[] = nodos.get(x).buscarRutas();
            this.digram = nodos.get(x).getDiagrama();
            try {
                Fin.getItems().clear();
                Fin.getItems().addAll(Arrays.asList(array));
            } catch (Exception es) {
            }

        });
        Fin.setOnAction((t) -> {
            y = Fin.getSelectionModel().getSelectedIndex();
        });
        Dialog dialog = new Dialog();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Nombre");
        TextField gas = new TextField();
        gas.setPromptText("Gasolina disponible");
        gas.setDisable(true);
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Tipo viaje"), 0, 1);
        grid.add(comboBox, 1, 1);

        grid.add(new Label("Seleccione el origen:"), 0, 2);
        grid.add(Nodos, 1, 2);

        grid.add(new Label("Seleccione el destino:"), 0, 3);
        grid.add(Fin, 1, 3);

        grid.add(new Label("Gasolina disponible:"), 0, 4);
        grid.add(gas, 1, 4);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get().equals(loginButtonType)) {
            //int tmp = nodos.get(x).obtener(Fin.getSelectionModel().getSelectedItem().toString());
            //nodos.get(x).getNodos().get(tmp).generar("red");
            //nodos.get(x).generar();
            String name=Fin.getSelectionModel().getSelectedItem().toString();
            for (int i = 0; i < nodos.size(); i++) {
               if(name.equals(nodos.get(i).getName())){
               y=i;
               break;
               }
            }
            isSelected = true;
        }

    }

}
