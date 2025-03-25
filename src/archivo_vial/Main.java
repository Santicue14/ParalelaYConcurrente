package archivo_vial;

import java.util.*;


public class Main {
    // Instancio mis clases para tener la utilidad
    private static CsvService csvService = new CsvService();
    private static String path = "src/vial.csv"; // Ruta del archivo CSV

    public static void main(String[] args) {
        ejecutarMenu(); // Llamamos a la función del menú
    }

    // Método para leer de forma secuencial
    public static void imprimirSecuencial() {
    	csvService.leerArchivoSecuencial(path);
    }

    // Método para leer de forma concurrente
    public static void imprimirConcurrente() {
    	csvService.leerArchivoConcurrente(path);
    }

    // Método para leer de forma paralela
    public static void imprimirParalelo() {
        
        csvService.leerArchivoParalelo(path);
        
    }

    // Método para ejecutar el menú
    public static void ejecutarMenu() {
        Scanner scanner = new Scanner(System.in); 

        while (true) {
            System.out.println("Hola Profe!\nElija una opción para probar los distintos tipos de leer el archivo vial.csv"
                    + "\n1-Cargar de forma secuencial."
                    + "\n2-Cargas de forma concurrente."
                    + "\n3-Cargar de forma paralela"
                    + "\n4-Mostrar los registros leídos"
                    + "\n5-Salir");
            
            String opcion = scanner.nextLine(); // Leer la opción
            
            switch (opcion) {
                case "1":
                    imprimirSecuencial(); // Llamada al metodo secuencial
                    continue;
                case "2":
                    imprimirConcurrente(); // Llamada al metodo concurrente
                    continue;
                case "3":
                    imprimirParalelo(); // Llamada al metodo paralelo
                    continue;
                case "4":
                	csvService.mostrarLineas();
                	continue;
                case "5":
                    System.out.println("Chau!");
                    scanner.close();
                    return; // Sale del bucle y termina el programa
                default:
                    System.out.println("Opción no válida.\n");
            }
        }
    }
}
