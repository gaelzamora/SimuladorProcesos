package project;

class Proceso {
    public int idProceso;
    public String nombreProceso;
    public int tamanioProceso;
    public int tiempoEjecucion;
    public int tiempoLlegada;
    public float execute;

    public Proceso(int idProceso, String nombreProceso, int tamanioProceso, int tiempoEjecucion, int tiempoLlegada) {
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
        this.tamanioProceso = tamanioProceso;
        this.tiempoEjecucion = tiempoEjecucion;
        this.tiempoLlegada = tiempoLlegada;
        this.execute = 0;
    }

    public float getExecute(float quantum) {
        this.execute = quantum;
        if(this.execute != (int) this.execute) {
            this.execute = (int) this.execute;
        }
        else {
            this.execute--;     
        }

        return this.execute;
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