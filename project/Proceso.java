package project;

class Proceso {
    private int idProceso;
    private String nombreProceso;
    private int tamanioProceso;
    private int tiempoEjecucion;
    private int tiempoLlegada;

    public Proceso(int idProceso, String nombreProceso, int tamanioProceso, int tiempoEjecucion, int tiempoLlegada) {
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
        this.tamanioProceso = tamanioProceso;
        this.tiempoEjecucion = tiempoEjecucion;
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public int getTamanioProceso() {
        return tamanioProceso;
    }

    public void setTamanioProceso(int tamanioProceso) {
        this.tamanioProceso = tamanioProceso;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public void reducirTiempoEjecucion() {
        tiempoEjecucion--;
    }

    @Override
    public String toString() {
        return "            Proceso{" +
                "idProceso=" + idProceso +
                ", nombreProceso='" + nombreProceso + '\'' +
                ", tiempoEjecucion=" + tiempoEjecucion +
                '}';
    }
}