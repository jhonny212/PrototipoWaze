package Main;

import ClassesForInterfaz.AnalizarArchivo;
import ClassesForInterfaz.ControladorVistaInicio;
import ClassesForInterfaz.controlDecamino;
import ClassesForInterfaz.nuevoViaje;
import EstructuraArbolB.ArbolB;
import EstructuraArbolB.ruta;
import estructuraGrafo.TablaTransiciones;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static String path;
    private static Scene initialScene;
    public static Scene View;
    private double offSetX, offSetY, zoomlvl;
    public static ControladorVistaInicio visorDeImagen;
    public static TablaTransiciones tabla;
    private static VBox contenido;
    nuevoViaje viaje;
    boolean is;

    public static void setView(Scene View) {
        Main.View = View;
    }

    public static VBox getContenido() {
        return contenido;
    }

    public static String getPath() {
        return path;
    }
    AnalizarArchivo file;

    @Override
    public void start(Stage s) throws Exception {
        is = false;
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        Label hint = new Label("Select Your Image");
        TextField URL = new TextField();
        URL.setEditable(false);
        URL.setPrefWidth(350);
        Button browse = new Button("Browse");
        FileChooser fc = new FileChooser();
        ExtensionFilter txt = new ExtensionFilter("txt", "*.txt");
        fc.getExtensionFilters().add(txt);
        browse.setOnAction(e -> {
            try {
                URL.setText(fc.showOpenDialog(s).getAbsolutePath());
            } catch (NullPointerException es) {
            }

        });
        Button open = new Button("Abrir");

        open.setOnAction(e -> {
            path = URL.getText();
            visorDeImagen = new ControladorVistaInicio();
            try {
                path = "/home/jhonny/Escritorio/DocumentoDeEntrada.txt";
                file = new AnalizarArchivo(path);
                if (file.crearArregrlo()) {
                    tabla = new TablaTransiciones(file.getListadoRutas(), file.getNodosGrafo());
                    path = tabla.getPath();
                    Menu m = new Menu("Opciones");
                    MenuItem m1 = new MenuItem("Nuevo viaje");
                    MenuItem m2 = new MenuItem("Reportes del viaje");
                    MenuItem m3 = new MenuItem("Ayuda");
                    m.getItems().add(m1);
                    m.getItems().add(m2);
                    m.getItems().add(m3);
                    MenuBar mb = new MenuBar();
                    mb.getMenus().add(m);
                    VBox vb = new VBox(20);
                    HBox panel = new HBox(15);
                    VBox data = visorDeImagen.initView(path);
                    controlDecamino ctrl = new controlDecamino(file.getNodosGrafo());
                    contenido = new VBox(5);
                    ctrl.crearContent();
                    panel.getChildren().addAll(data, contenido);
                    vb.getChildren().addAll(mb, panel);
                    View = new Scene(vb, 900, 620);
                    m1.setOnAction((t) -> {
                        viaje = new nuevoViaje();
                        viaje.init(file.getNodosGrafo());
                        if (viaje.isIsSelected()) {
                            is = true;
                            ctrl.modificar(viaje.getOrigen(), viaje.getDestino(),viaje.x,viaje.y);
                        }
                    });
                    m2.setOnAction((t) -> {
                        if (true) {
                            abrirReportes(viaje.x, viaje.y);
                        }
                    });
                    s.setScene(View);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "expecion");
            }

        });
        grid.add(hint, 0, 0);
        grid.add(URL, 1, 0);
        grid.add(browse, 2, 0);
        grid.add(open, 2, 1);
        initialScene = new Scene(grid, 700, 100);
        s.setScene(initialScene);
        s.setResizable(false);
        s.show();

    }

    public static void main(String[] args) {
        //
        
        //launch(args);
        ArbolB t = new ArbolB();
        t.insertarDato(new ruta(null, null, 50, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 49, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 48, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 47, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 40, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 39, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 46, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 45, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 44, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 43, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 42, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 41, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 38, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 37, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 36, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 35, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 34, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 33, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 32, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 31, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 30, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 29, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 28, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 27, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 26, 0, 0, 0));
        t.insertarDato(new ruta(null, null, 25, 0, 0, 0));
        t.imprimir();
        t.eliminar(25);
        /*t.insertarDato(new ruta(null, null, 25, 0, 0, 0));
        t.eliminar(25);
        t.eliminar(34);
        t.eliminar(38);
        t.eliminar(37);
        t.eliminar(35);
        //t.eliminar(40);*/
        
//t.eliminar(31);
                
//t.insertarDato(new ruta(null, null, -17, 0, 0, 0));
    }

    private void abrirReportes(int inicio, int fin) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Reports.fxml"));
            root = (Parent) fxmlLoader.load();
            ReportsController controller = fxmlLoader.<ReportsController>getController();
            controller.inicializar(inicio, fin, this.file.getNodosGrafo().size(), this.file.getNodosGrafo());

        } catch (IOException ex) {
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
