package estructuraGrafo;
import java_cup.runtime.Symbol;
%%
%class lexicoGrafo
%cup
%cupdebug
%line
%column
%full
%char
%public
/*Identifiers*/
letra=[a-zA-z]
numero=[0-9]
simbolo=[-_$]
punto=[.]
%{
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }
%}
%%
<YYINITIAL>{
    "<"                                            {return symbol(sym.menor,new String(yytext()));}
    ">"                                            {return symbol(sym.mayor,new String(yytext()));}
    (({numero})|({letra})|({simbolo})|({punto}))*     {return symbol(sym.id,new String(yytext()));}
    "|"                                            {return symbol(sym.or,new String(yytext()));}
    (("\n")|("\t"))*                        {}
}
//------------------------------------------------------>
private static void evaluar() {
        parser parser=null;
        parser.listadoDeRutas = new ArrayList();
        try {
            String txt="<f_en_el_chat>|<ostia>|<10.2>|<5.4>|<10.2>";
            lexicoGrafo scan = new lexicoGrafo(new BufferedReader(new StringReader(txt)));
            parser = new parser(scan);
            parser.parse();
        } catch (Exception ex) {
            System.out.println("ACA->");
            ex.printStackTrace();
        }
    }

    public  static void generarCompilador(){
        try {
            String ruta = "src/estructuraGrafo/"; //ruta donde tenemos los archivos con extension .jflex y .cup
            String opcFlex[] = {ruta + "lexico.jFlex", "-d", ruta};
            jflex.Main.generate(opcFlex);
            String opcCUP[] = {"-destdir", ruta, "-parser", "parser", ruta + "parser.cup"};
            java_cup.Main.main(opcCUP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }