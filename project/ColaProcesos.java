package project;


class ColaProcesos {
    public Proceso[] procesos;
    private int frente;
    private int finalCola;
    private int capacidad;

    public ColaProcesos(int capacidad) {
        this.capacidad = capacidad;
        procesos = new Proceso[capacidad];
        frente = 0;
        finalCola = -1;
    }

    public void enqueue(Proceso proceso) {
        if (finalCola == capacidad - 1) {
            System.out.println("La cola esta llena");
        } else {
            procesos[++finalCola] = proceso;
        }
    }

    public Proceso dequeue() {
        if (isEmpty()) {
            System.out.println("La cola esta vacia");
            return null;
        } else {
            Proceso temp = procesos[frente];
            for (int i = 0; i < finalCola; i++) {
                procesos[i] = procesos[i + 1];
            }
            finalCola--;
            return temp;
        }
    }

    public Proceso peek() { 
        if (isEmpty()) {
            System.out.println("La cola esta vacia");
            return null;
        } else {
            return procesos[frente];
        }
    }

    public void printQueue() {
        for (int i = frente; i <= finalCola; i++) {
            System.out.println(procesos[i]);
        }
    }

    public int size() {
        return finalCola + 1;
    }

    public boolean isEmpty() {
        return (finalCola == -1);
    }
}