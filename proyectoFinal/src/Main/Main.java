package Main;

import ClassesForInterfaz.AnalizarArchivo;
import ClassesForInterfaz.ControladorVistaInicio;
import ClassesForInterfaz.controlDecamino;
import ClassesForInterfaz.nuevoViaje;
import EstructuraArbolB.ArbolB;
import EstructuraArbolB.ruta;
import estructuraGrafo.TablaTransiciones;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;

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
                file = new AnalizarArchivo(path);
                if (file.crearArregrlo()) {
                    tabla = new TablaTransiciones(file.getListadoRutas(), file.getNodosGrafo());
                    Thread.sleep(3000);
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
                    data.setStyle("-fx-background-color: #9FA4B6;");
                    controlDecamino ctrl = new controlDecamino(file.getNodosGrafo());
                    contenido = new VBox(5);
                    ctrl.crearContent();
                    panel.getChildren().addAll(data, contenido);
                    vb.getChildren().addAll(mb, panel);
                    vb.setStyle("-fx-background-color: #B7B7B7;");
                    View = new Scene(vb, 900, 620);
                    m1.setOnAction((t) -> {
                        viaje = new nuevoViaje();
                        viaje.init(file.getNodosGrafo());
                        if (viaje.isIsSelected()) {
                            is = true;
                            ctrl.modificar(viaje.getOrigen(), viaje.getDestino(), viaje.x, viaje.y,viaje.isApie);
                        }
                    });
                    m2.setOnAction((t) -> {
                        if (is) {
                            abrirReportes(viaje.x, viaje.y);
                        }
                    });
                    s.setScene(View);
                }
            } catch (Exception ex) {
            }

        });
        grid.add(hint, 0, 0);
        grid.add(URL, 1, 0);
        grid.add(browse, 2, 0);
        grid.add(open, 2, 1);
        initialScene = new Scene(grid, 700, 100);
        s.setScene(initialScene);
        s.show();

    }

    public static void main(String[] args) {
      launch(args);
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
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
