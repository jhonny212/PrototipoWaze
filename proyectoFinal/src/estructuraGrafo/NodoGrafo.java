package estructuraGrafo;

import ClassesForInterfaz.href;
import ClassesForInterfaz.nodosForDiagram;
import EstructuraArbolB.ruta;

import java.util.ArrayList;

public class NodoGrafo {

    ArrayList<NodoGrafo> href;
    ArrayList<ruta> ruta;
    final String name;
    ArrayList<String> isChech;
    private String diagrama;
    private ArrayList<href> referencias;
    private ArrayList<nodosForDiagram> nodos;

    public ArrayList<nodosForDiagram> getNodos() {
        return nodos;
    }

    public String getDiagrama() {
        return diagrama;
    }

    public NodoGrafo(String name) {
        href = new ArrayList<>();
        ruta = new ArrayList<>();
        this.name = name;
        this.isChech = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<NodoGrafo> getHref() {
        return href;
    }

    public void setHref(ArrayList<NodoGrafo> href) {
        this.href = href;
    }

    public ArrayList<ruta> getRuta() {
        return ruta;
    }

    public void setRuta(ArrayList<ruta> ruta) {
        this.ruta = ruta;
    }
    String prueba="";
    public String[] buscarRutas() {
        diagrama = "";
        //this.nodos = new ArrayList<>();
        //this.referencias = new ArrayList<>();
        //this.nodos.add(new nodosForDiagram(this.name, "yellow"));
        isChech = new ArrayList<>();
        String lugares = search(this);
        String array[] = lugares.split("\n");
        //href t = new href();
        //this.diagrama = t.generarDiagrama(referencias, nodos);
        return array;
    }

    private String search(NodoGrafo aThis) {
        String lugares = "";

        for (int i = 0; i < aThis.href.size(); i++) {
            String name = aThis.href.get(i).name;
            if (exists(name, this.isChech)) {
             //   this.nodos.add(new nodosForDiagram(name, "yellow"));
             //   this.referencias.add(new href(name, aThis.name));
                lugares += name + "\n";
                this.isChech.add(name);
                lugares += search(aThis.href.get(i));
            }
        }
       
        return lugares;
    }

    private boolean exists(String name, ArrayList<String> ref) {
        for (int i = 0; i < ref.size(); i++) {
            if (name.equals(ref.get(i))) {
                return false;
            }

        }

        return true;
    }

    public void generar() {
        href t = new href();
        this.diagrama = t.generarDiagrama(referencias, nodos);
    }

    public int obtener(String name) {
        for (int i = 0; i < this.nodos.size(); i++) {
            if(this.nodos.get(i).getNameNodo().equals(name)){
            return i;}
    }
        return 0;
    }
}
