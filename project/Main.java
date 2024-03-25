package project;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t_max = 0;
        float ejecuto = 0;
        float esp_max = 0;
        
        System.out.print("Ingrese el tiempo de quantum: ");
        int q = scanner.nextInt();
        
        System.out.print("Ingrese la cantidad de procesos a crear: ");
        int n = scanner.nextInt();

        Value t_ejecuto = new Value(n);
        Value t_espera_max = new Value(n);
        Value t_llegada = new Value(n);
        Formula formula = new Formula(n);
        Proceso[] procesos = new Proceso[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nIngrese los datos del proceso " + (i + 1) + ":");
            scanner.nextLine(); // Limpiar el buffer del scanner
            System.out.print("Nombre del proceso: ");
            String nombreProceso = scanner.nextLine();
            System.out.print("Tamanio del proceso: ");
            int tamanioProceso = scanner.nextInt();
            System.out.print("Tiempo de servicio: ");
            int tiempoEjecucion = scanner.nextInt();
            System.out.print("Tiempo de llegada: ");
            int tiempoLlegada = scanner.nextInt();

            procesos[i] = new Proceso(i + 1, nombreProceso, tamanioProceso, tiempoEjecucion-1, tiempoLlegada);
            t_max += procesos[i].getTiempoEjecucion();
            
            // 12 / 4 = 3
            float q_proceso = procesos[i].getTiempoEjecucion() / q;
            // 2 * 4
            ejecuto = procesos[i].getExecute(q_proceso) * q;
            t_ejecuto.addValue(ejecuto);
            t_llegada.addValue(procesos[i].getTiempoLlegada());
        }

        scanner.close();

        ColaProcesos colaProcesos = new ColaProcesos(n);
        ColaProcesos ram = new ColaProcesos(n); // Nueva cola ram
        int capacidad = 5; // Capacidad de la cola ram
        int ejecucion_flag = 0; // Variable para controlar el proceso en ejecucion
        int qt=0;
        int tiempoActual = 0;

        while (tiempoActual<t_max+1) {
            System.out.println("Tiempo actual: " + tiempoActual);
            boolean is_time = false;
            boolean flag2 = false;

            //Poblar la cola de procesos en funcion del tiempo de llegada
            for(int i = 0; i<n; i++){

                if (procesos[i].getTiempoLlegada()==tiempoActual){
                    
                    colaProcesos.enqueue(procesos[i]);
                    is_time = true;
                    procesos[i].setTiempoEjecucion(procesos[i].getTiempoEjecucion()-1);
                }
            }
            // Imprimir cola de procesos solo si se inserta un nuevo proceso
            
            if (is_time) {
                System.out.println("    Cola de procesos:");
                colaProcesos.printQueue();
            }
           
           // Procesamiento de la cola RAM (Ccolocar elementos de cola procesos a ram solo si existe la capacidad)
            for(int i = 0; i<colaProcesos.size();i++){
                Proceso proceso = colaProcesos.peek();
                if (proceso.getTamanioProceso() <= capacidad) {
                    System.out.println("    Hay espacio para el proceso:");
                    // 2
                    capacidad=capacidad-proceso.getTamanioProceso();
                    
                    colaProcesos.dequeue();
                    ram.enqueue(proceso);
                    flag2 = true;
                } else {
                    System.out.println("    NO hay espacio para el proceso:");
                    break;
                }
            }
            
            if (flag2) {
                System.out.println("    Cola de procesos en RAM:");
                ram.printQueue();
            }
           
            
            //Procesamiento del proceso en ejecucion
            
            //Imprimir el proceso ejecutandoce, restar tiempo y interrupir por quantum
            if(ejecucion_flag==1){
                qt++;
                System.out.println("    Tiempo en cuantum del proceso: " + qt);
                Proceso proceso = ram.peek();
                System.out.println("    Ejecutando proceso " + proceso.getNombreProceso() + " t: " + proceso.getTiempoEjecucion());
                proceso.setTiempoEjecucion(proceso.getTiempoEjecucion()-1);
                //interrumpir por cuantum
                if (qt==q){
                    System.out.println("    Termina el quantum de: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                    ram.dequeue();
                    ram.enqueue(proceso);
                    ejecucion_flag = 0;
                }
            }
            //Finaliza la ejecucion.   
            if(ejecucion_flag==1){
                Proceso proceso = ram.peek();
                esp_max = 0;
                if (proceso.getTiempoEjecucion()<=0){
                    esp_max = tiempoActual;
                    
                    t_espera_max.addValue(esp_max);
                    t_espera_max.printValues();
                    System.out.println("// -----    Termina la ejecucion de: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                    ram.dequeue();
                    //regresar el espacio del proceso terminado.
                    capacidad=capacidad+proceso.getTamanioProceso();
                    ejecucion_flag = 0;
                }
            }
            //Nuevo proceso a ejecutarse.
            if (!ram.isEmpty() && ejecucion_flag == 0){
                Proceso proceso = ram.peek();

                ejecucion_flag = 1;
                System.out.println("   Ejecutando proceso: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                //Se inicia o reinicia el contador de quantum
                qt=0;
            }
            tiempoActual++;
            
            
        }
        float tiempo_esp = 0;
        
        if(n>=0) {
            for(int i = 0; i < n; i++) {
                System.out.println("t_espera_max es: "+t_espera_max.getValue(i)+" Tiempo de llegada: "+t_llegada.getValue(i)+" Tiempo que se ejecuto: "+t_ejecuto.getValue(i));
                tiempo_esp = (t_espera_max.getValue(i)) - (t_llegada.getValue(i)) - (t_ejecuto.getValue(i));
                formula.addValue((int) tiempo_esp);
                tiempo_esp = 0;
            }
            System.out.println("El promedio de espera es: "+formula.totalFormula());
        }
    }
}