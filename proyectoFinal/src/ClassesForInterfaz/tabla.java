/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesForInterfaz;

/**
 *
 * @author jhonny
 */
public class tabla {
    private  double dato;
    private  String vertice;
    private int next;
    public tabla(double dato, String vertice,int next) {
        this.dato = dato;
        this.vertice = vertice;
        this.next=next;
    }
    public int getInt(){
    return this.next;}
    public double getDato() {
        return dato;
    }

    public String getVertice() {
        return vertice;
    }

    public void setDato(double dato) {
        this.dato = dato;
    }

    public void setVertice(String vertice) {
        this.vertice = vertice;
    }
    public void setInt(int next) {
        this.next=next;
    }
    
}
