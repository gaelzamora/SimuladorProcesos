package project;

class Proceso {
    public int idProceso;
    public String nombreProceso;
    public int tamanioProceso;
    public int tiempoEjecucion;
    public int tiempoLlegada;
    public float t_exe_max;
    public float t_esp_max;
    public float[] executes;
    public int index;
    public float execute;
    public int up;
    // [22, 24, 25]
    //if(arreglo.length>2) {
        // this.execute = arreglo[-2];
    //}
    public Proceso(int idProceso, String nombreProceso, int tamanioProceso, int tiempoEjecucion, int tiempoLlegada) {
        this.idProceso = idProceso;
        this.nombreProceso = nombreProceso;
        this.tamanioProceso = tamanioProceso;
        this.tiempoEjecucion = tiempoEjecucion;
        this.tiempoLlegada = tiempoLlegada;
        this.t_exe_max = 0;
        this.t_esp_max = 0;
        this.index = 0;
        this.execute = 0;
        this.executes = new float[20];
        this.up = 0;
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

    public float getExecute() {
        if(this.index > 0) {
            return this.executes[this.index-1];
        }


        return this.execute;
    }

    public void setExecute(float qt) {
        this.execute += qt;

        this.executes[this.index] = this.execute;
        this.index += 1;
    }

    public void setExeMax(float t_exe) {
        this.t_exe_max = t_exe;
    }

    public float getExeMax() {
        return this.t_exe_max;

    }
    // 2
    public void setEspMax(int qt) {
        if(qt < this.t_exe_max) {
            this.t_esp_max = this.t_exe_max - qt;
        }
    }
    public float getEspMax() {
        return this.t_esp_max+1;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getUp() {
        return this.up;
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