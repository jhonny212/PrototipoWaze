package EstructuraArbolB;

public class ruta {

    private lugar origen, destino;
    private double tiempoVehiculo, tiempoPie, consumoGas, desgastePersona;
    private double clave;
    private String name;
    public ruta(lugar origen, lugar destino, double tiempoVehiculo, double tiempoPie, double consumoGas, double desgastePersona) {
        this.origen = origen;
        this.destino = destino;
        this.tiempoVehiculo = tiempoVehiculo;
        this.tiempoPie = tiempoPie;
        this.consumoGas = consumoGas;
        this.desgastePersona = desgastePersona;
        this.clave = tiempoVehiculo;
    }

    public lugar getOrigen() {
        return origen;
    }

    public lugar getDestino() {
        return destino;
    }

    public double getTiempoVehiculo() {
        return tiempoVehiculo;
    }

    public double getTiempoPie() {
        return tiempoPie;
    }

    public double getConsumoGas() {
        return consumoGas;
    }

    public double getDesgastePersona() {
        return desgastePersona;
    }

    public double getClave() {
        return clave;
    }

    public void setClave(double clave) {
        this.clave = clave;
    }

    public ruta(double clave, String name) {
        this.clave = clave;
        this.name=name;
    }

    public String getName() {
        return name;
    }
    
}
