package project;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese el tiempo de quantum: ");
        int q = scanner.nextInt();
        
        System.out.print("Ingrese la cantidad de procesos a crear: ");
        int n = scanner.nextInt();
        
        //Se crea cada Formula a resolver
        Formula t_esp = new Formula(n);
        Formula t_ejecucion = new Formula(n);
        Formula t_respuesta = new Formula(n);

        //Se crea un arreglo que almacena entidades de Proceso 
        //con una longitud de n que es la que dio el usuario 
        Proceso[] procesos = new Proceso[n];
        
        //Se crea mapa hash para saber cuando un proceso suba por primera vez a la CPU
        HashMap<String, Integer> hashMap = new HashMap<>();
        
        //Se crea mapa hash para guardar procesos activos en la cola
        HashMap<String, Integer> mapProcess = new HashMap<>();

        // Se hace la captura de los procesos en el arreglo y se agregan los procesos en el mapa hash 
        //de mapProcess
        for (int i = 0; i < n; i++) {
            System.out.println("\nIngrese los datos del proceso " + (i+1) + ":");
            scanner.nextLine(); // Limpiar el buffer del scanner
            System.out.print("Nombre del proceso: ");
            String nombreProceso = scanner.nextLine();
            System.out.print("Tamanio del proceso: ");
            int tamanioProceso = scanner.nextInt();
            System.out.print("Tiempo de servicio: ");
            int tiempoEjecucion = scanner.nextInt();
            System.out.print("Tiempo de llegada: ");
            int tiempoLlegada = scanner.nextInt();

            procesos[i] = new Proceso(i, nombreProceso, tamanioProceso, tiempoEjecucion, tiempoLlegada);

            mapProcess.put(procesos[i].getNombreProceso(), procesos[i].getIdProceso());
        }

        scanner.close();

        ColaProcesos colaProcesos = new ColaProcesos(n);
        ColaProcesos ram = new ColaProcesos(n); // Nueva cola ram
        int capacidad = 100; // Capacidad de la cola ram
        int ejecucion_flag = 0; // Variable para controlar el proceso en ejecucion
        int qt=0;
        int tiempoActual = 0;
        int encolaciones = 0; //variable que cuenta las veces que se ha encolado a RAM
        //int tiempo_max = 100; //variable para establecer el tiempo maximo


        Proceso procesoAux = new Proceso(0, "0", 0, 0, 0); //proceso auxiliar para encolar cuando acaba el quantum
        boolean quantum_flag=false; //bandera para indicar cuando encolar por quantum

        while (!mapProcess.isEmpty()) {
            
            System.out.println("Tiempo actual: " + tiempoActual);
            
            /*if (ram.isEmpty()&&colaProcesos.isEmpty()&&(encolaciones==n)){
                break;
            }*/
            
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
           
           // Procesamiento de la cola RAM (Colocar elementos de cola procesos a ram solo si existe la capacidad)
            if (colaProcesos.size() != 0) {
                int size = colaProcesos.size(); // Guardamos el tamanio inicial de la cola
                for (int i = 0; i < size; i++) { // Iteramos sobre el tamanio inicial de la cola
                    Proceso proceso = colaProcesos.dequeue(); // Obtenemos el siguiente proceso de la cola
                    if (proceso.getTamanioProceso() <= capacidad) {
                        encolaciones++;
                        capacidad -= proceso.getTamanioProceso();
                        ram.enqueue(proceso);
                        
                        flag2 = true;
                        System.out.println("    Hay espacio para el proceso:" + encolaciones);
                    } else {
                        System.out.println("    NO hay espacio para el proceso:");
                        break;
                    }
                }
            }   
            if (quantum_flag==true){
                ram.enqueue(procesoAux);
                quantum_flag=false;
            }
            
            /*if (flag2) {
                System.out.println("    Cola de procesos en RAM:");
                ram.printQueue();
            }*/
            
            //Procesamiento del proceso en ejecucion
            
            //Imprimir el proceso ejecutandoce, restar tiempo e interrupir por quantum
           
            Proceso proceso = ram.peek();
            //Nuevo proceso a ejecutarse.
            if (!ram.isEmpty() && ejecucion_flag == 0){
                //NO HAY PROCESO EN EJECUCION
                
                ejecucion_flag = 1;
                // true
                // is_execute = true;

                System.out.println("    Ejecutando proceso: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                qt=0; //Se inicia o reinicia el contador de quantum 
                if(!hashMap.containsKey(proceso.getNombreProceso())) {
                    hashMap.put(proceso.getNombreProceso(), tiempoActual);
                    proceso.setUp(tiempoActual);
                }
            }
            if (ejecucion_flag==1){
                //HAY PROCESO EN EJECUCION
                
                qt++; //Se incrementa el tiempo que lleva en quantum
                System.out.println("    Ejecutando proceso " + proceso.getNombreProceso() + "  t: " + proceso.getTiempoEjecucion() + "  qt: " + qt);
                proceso.setTiempoEjecucion(proceso.getTiempoEjecucion()-1);

                if(proceso.getTiempoEjecucion()<0){
                    
                    proceso.setExeMax(tiempoActual+1);
                    //FINALIZA EJECUCION
                    proceso.setEspMax(qt);
                    System.out.println("// -----    Termina la ejecucion de: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                    
                    ram.dequeue();
                    //regresar el espacio del proceso terminado.
                    capacidad=capacidad+proceso.getTamanioProceso();
                    ejecucion_flag = 0;
                    mapProcess.remove(proceso.getNombreProceso());
                }else if (qt==q){
                    //INTERRUMPIR POR CUANTUM   
                    proceso.setExecute(qt);
                    System.out.println("    Termina el quantum de: " + proceso.getNombreProceso() + " (ID: " + proceso.getIdProceso() + ")");
                    ram.dequeue();
                    procesoAux=proceso;
                    quantum_flag=true;
                    ejecucion_flag = 0;
                }
            }
            tiempoActual++;
        }

        for(Proceso proceso : procesos) {
            System.out.println(proceso.getNombreProceso());
        }
    
        
        if(n>0) {
            for(int i = 0; i < n; i++) {
                float tiempo_esp = (procesos[i].getEspMax()) - (procesos[i].getTiempoLlegada()) - (procesos[i].getExecute());
                System.out.println(" --------- Proceso: " + procesos[i].getNombreProceso() +" Esp Max: " + procesos[i].getEspMax() + " - " + " Tiempo de llegada: " 
                + procesos[i].getTiempoLlegada() + " - " + " Se ejecuto: " + procesos[i].getExecute());
                t_esp.addValue((int) tiempo_esp);

                System.out.println("/---------------------------/");

                float tiempo_res = (procesos[i].getUp()) - (procesos[i].getTiempoLlegada());
                System.out.println("---------- subio en tiempo: "+procesos[i].getUp() + " Tiempo Llegada: "
                +procesos[i].getTiempoLlegada());
                t_respuesta.addValue((int) tiempo_res);

                System.out.println("---------------------------------------------");

                float tiempo_ejec = (procesos[i].getExeMax()) - (procesos[i].getTiempoLlegada());
                System.out.println("---------- Se ejecuto hasta tiempo: "+procesos[i].getExeMax() + 
                " Tiempo Llegada: " + procesos[i].getTiempoLlegada());
                t_ejecucion.addValue((int) tiempo_ejec);

            }
            System.out.println("// ---- El promedio de espera es: "+t_esp.totalFormula());
            System.out.println("// ---- El promedio de respuesta es: "+t_respuesta.totalFormula());
            System.out.println("// ---- El promedio de ejecucion es: "+t_ejecucion.totalFormula());
        }
    }
}