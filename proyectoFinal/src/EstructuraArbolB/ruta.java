package EstructuraArbolB;

public class ruta {
    private final lugar origen,destino;
    private final double tiempoVehiculo,tiempoPie,consumoGas,desgastePersona,clave;

    public ruta(lugar origen, lugar destino, double tiempoVehiculo, double tiempoPie, double consumoGas, double desgastePersona) {
        this.origen = origen;
        this.destino = destino;
        this.tiempoVehiculo = tiempoVehiculo;
        this.tiempoPie = tiempoPie;
        this.consumoGas = consumoGas;
        this.desgastePersona = desgastePersona;
        this.clave=tiempoVehiculo;
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

   
}
