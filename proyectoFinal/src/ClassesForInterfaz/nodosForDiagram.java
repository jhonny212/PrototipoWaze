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
public class nodosForDiagram {
    private final String nameNodo,color;
    private String label;
    

    public nodosForDiagram(String nameNodo, String color) {
        this.nameNodo = nameNodo;
        this.color = color;
       
        generar(color);
    }

    public void generar(String color) {
        this.label=this.nameNodo+" [fillcolor="+color+", style=\"rounded,filled\", shape=circle, label="+nameNodo+"]";
    }

    public String getNameNodo() {
        return nameNodo;
    }

    public String getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

   
    
}
