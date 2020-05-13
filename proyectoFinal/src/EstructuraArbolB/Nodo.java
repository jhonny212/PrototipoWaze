package EstructuraArbolB;

public class Nodo {
    NodoB hijoIzq,hijoDer;
    boolean continuar;
    private ruta dato;
    int key;
    public Nodo(ruta rta,int key){
        hijoDer=null;
        hijoIzq=null;
        this.dato=rta;
        this.key=key;
        continuar=false;
    }


    public ruta getDato(){
        return dato;
    }

    public void setDato(ruta dato) {
        this.dato = dato;
    }
}
