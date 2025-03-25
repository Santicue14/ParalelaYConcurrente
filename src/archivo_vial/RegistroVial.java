package archivo_vial;

public class RegistroVial {
	
	//Declaro los atributos del objeto
	private int numeroHoja;
	private String tipoObjeto;
	private int identificadorTramo;
	private String tipoCamino;
	private double longitud;
	
	
	//Método Constructor
	RegistroVial(int numeroHoja, String tipoObjeto, int identificadorTramo, String tipoCamino, double longitud){
		this.numeroHoja = numeroHoja;
		this.tipoObjeto = tipoObjeto;
		this.identificadorTramo = identificadorTramo;
		this.tipoCamino = tipoCamino;
		this.longitud = longitud;
	}
	
	//Mostrar info de cada archivo con un salto de línea
	public void mostrarDetalle() {
	    System.out.println("N° Hoja: " + this.numeroHoja + 
	                       ", Tipo Objeto: " + this.tipoObjeto + 
	                       ", Identificador Tramo: " + this.identificadorTramo + 
	                       ", Tipo Camino: " + this.tipoCamino + 
	                       ", Longitud: " + this.longitud);
	}

}
