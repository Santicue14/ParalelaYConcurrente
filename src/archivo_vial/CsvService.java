package archivo_vial;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class CsvService {
		
	private List<RegistroVial> registros = new ArrayList<>();
	
    // Método secuencial
    public void leerArchivoSecuencial(String path) {
    	long inicio = System.currentTimeMillis(); // Tomamos el tiempo actual
        registros = new ArrayList<>(); //Reiniciar la lista
        
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String linea;
            //En cada campo asigna el valor correspondiente
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                int numHoja = Integer.parseInt(campos[0]);
                String tipoObjeto = campos[1];
                int idTramo = Integer.parseInt(campos[2]);
                String tipoCamino = campos[3];
                double longitud = Double.parseDouble(campos[4]);
                RegistroVial regTemp = new RegistroVial(numHoja, tipoObjeto, idTramo, tipoCamino, longitud);
                registros.add(regTemp);
            }
           long fin = System.currentTimeMillis(); // Tomamos el tiempo actual y calculamos la diferencia
           System.out.println("Tiempo de ejecución secuencial: " + (fin - inicio) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Método concurrente utilizando ExecutorService
    public void leerArchivoConcurrente(String path) {
    	long inicio = System.currentTimeMillis(); // Tomamos el tiempo actual
        registros = Collections.synchronizedList(new ArrayList<>()); //Aseguramos de que la lista funcione bien al ser concurrente
        ExecutorService executor = Executors.newFixedThreadPool(6); // 6 hilos

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))){
        	List<String> bloque = new ArrayList<>();
        	String linea;
        	
        	while((linea = br.readLine())!= null) {
        		bloque.add(linea);
        		if(bloque.size() >= 100) {
        			List<String> bloqueProcesar = new ArrayList<>(bloque);
        			executor.execute(()->procesarLineas(bloqueProcesar));
        			bloque.clear();
        		}
        }
        if(!bloque.isEmpty()) {
        	executor.execute(()->procesarLineas(new ArrayList<>(bloque)));
        }
        }catch (IOException e) {
        	e.printStackTrace();
        }
        executor.shutdown();
        
        long fin = System.currentTimeMillis(); // Tomamos el tiempo actual y calculamos la diferencia
        System.out.println("Tiempo de ejecución concurrente: " + (fin - inicio) + " ms");
    }
        
        
    // Método paralelo utilizando parallelStream()
    public List<RegistroVial> leerArchivoParalelo(String path) {
        List<RegistroVial> registros = new ArrayList<>();
        long inicio = System.currentTimeMillis(); // Tomamos el tiempo actual
        try {
            // Leemos las líneas del archivo en paralelo
            Files.lines(Paths.get(path))
                 .parallel() // Procesamos las líneas en paralelo
                 .forEach(linea -> {
                     String[] campos = linea.split(",");
                     int numHoja = Integer.parseInt(campos[0]);
                     String tipoObjeto = campos[1];
                     int idTramo = Integer.parseInt(campos[2]);
                     String tipoCamino = campos[3];
                     double longitud = Double.parseDouble(campos[4]);
                     RegistroVial regTemp = new RegistroVial(numHoja, tipoObjeto, idTramo, tipoCamino, longitud);
                     synchronized (registros) { // Sincronizamos el acceso a la lista
                         registros.add(regTemp);
                     }
                 });
            long fin = System.currentTimeMillis(); // Tomamos el tiempo actual y calculamos la diferencia
            System.out.println("Tiempo de ejecución paralelo: " + (fin - inicio) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return registros;
    }
    
    private void procesarLineas(List<String> lineas) {
    	for(String linea: lineas) {
    		String[] campos = linea.split(",");
            int numHoja = Integer.parseInt(campos[0]);
            String tipoObjeto = campos[1];
            int idTramo = Integer.parseInt(campos[2]);
            String tipoCamino = campos[3];
            double longitud = Double.parseDouble(campos[4]);
            RegistroVial regTemp = new RegistroVial(numHoja, tipoObjeto, idTramo, tipoCamino, longitud);
            synchronized(registros) {
            	registros.add(regTemp);
            }
    	}
    }
    
    public void mostrarLineas() {
    	if(registros.size()>0) {
    	for (RegistroVial registro: registros) {
    		registro.mostrarDetalle();
    	}
    }
    	else {
        	System.out.println("No se leyó el archivo");
        }
}
}
