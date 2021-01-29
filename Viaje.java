
public class Viaje {

	private String destino;
	private int cantidadDeKilometros;

	public Viaje(String destino, int cantidadDeKilometros) {
		if(destino == null || destino == "") {
			throw new RuntimeException("el destino no debe ser un string vacío");
		}
		if(cantidadDeKilometros < 0) {
			throw new RuntimeException("la cantidad de kilómetros no puede ser menor a cero");
		}
		this.destino = destino;
		this.cantidadDeKilometros = cantidadDeKilometros;
	}

	public String getDestino() {
		return destino;
	}

	public int getCantidadDeKilometros() {
		return cantidadDeKilometros;
	} 
	
}
