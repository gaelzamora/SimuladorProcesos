package project;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese el tiempo de quanum: ");
        int q = scanner.nextInt();

        System.out.print("Ingrese la cantidad de procesos a crear: ");
        int n = scanner.nextInt();
        

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

            procesos[i] = new Proceso(i + 1, nombreProceso, tamanioProceso, tiempoEjecucion, tiempoLlegada);
        }

        scanner.close();


        ColaProcesos colaProcesos = new ColaProcesos(n);
        ColaProcesos ram = new ColaProcesos(n); // Nueva cola ram
        int capacidad = 5; // Capacidad de la cola ram
        int ejecucion_flag = 0; // Variable para controlar el proceso en ejecucion
        int qt=0;
        int tiempoActual = 0;

        while (tiempoActual<100) {
            System.out.println("Tiempo actual: " + tiempoActual);
            boolean is_time = false;
            boolean flag2 = false;
            // n = 3
            //Poblar la cola de procesos en funcion del tiempo de llegada
            for(int i = 0; i<n; i++){
                // nombre: hola, tamaño: 4, tiempo de servicio: 2, tiempo de llegada: 1
                // nombre: o, tamaño: 4, tiempo de servicio: 5, tiempo de llegada: 1
                // nombre: r, tamaño: 6, tiempo de servicio: 5, tiempo de llegada: 8

                //0 == 0
                // true
                if (procesos[i].getTiempoLlegada()==tiempoActual){
                    
                    colaProcesos.enqueue(procesos[i]);
                    is_time = true;
                }
            }
            // Imprimir cola de procesos solo si se inserta un nuevo proceso
            
            // true
            if (is_time) {
                System.out.println("    Cola de procesos:");
                colaProcesos.printQueue();
            }
           
           // Procesamiento de la cola RAM (Ccolocar elementos de cola procesos a ram solo si existe la capacidad)
           // 0 < 0
            for(int i = 0; i<colaProcesos.size();i++){
                // proceso = p1
                Proceso proceso = colaProcesos.peek();
                // 3 <= 5
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
            // true 
            if(ejecucion_flag==1){
                // p1
                Proceso proceso = ram.peek();
                // 0 == 0
                if (proceso.getTiempoEjecucion()==0){
                    System.out.println("// -----    Termina la ejecucion de: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                    ram.dequeue();
                    //regresar el espacio del proceso terminado.
                    capacidad=capacidad+proceso.getTamanioProceso();
                    ejecucion_flag = 0;
                }
            }
            //Nuevo proceso a ejecutarse.
            // true * true = true
            if (!ram.isEmpty() && ejecucion_flag == 0){
                // p1
                Proceso proceso = ram.peek();

                ejecucion_flag = 1;
                System.out.println("   Ejecutando proceso: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                //Se inicia o reinicia el contador de quantum
                qt=0;
            }
            tiempoActual++;
        }

    }

}




