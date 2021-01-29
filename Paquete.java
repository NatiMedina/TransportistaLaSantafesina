
public class Paquete {

	private int codigoDelPaquete;
	private double peso;
	private double volumen;
	private String destino;
	private boolean necesitaFrio;
	static private int codigo = 0;
	private double costoTercerizFrio;

	public Paquete(String destino, double peso, double volumen, boolean necesitaFrio) {
		if(peso <= 0 || volumen <= 0) {
			throw new RuntimeException("el peso y el volumen deben ser números positivos");
		}
		if(destino.length() == 0 || destino == "") {
			throw new RuntimeException("el destino debe contener al menos un caracter");
		}
		codigoDelPaquete = ++codigo;
		this.peso = peso;
		this.volumen = volumen;
		this.destino = destino;
		this.necesitaFrio = necesitaFrio;
		costoTercerizFrio = 0.00;
	}

	public int getCodigoDelPaquete() {
		return codigoDelPaquete;
	}

	public double getPeso() {
		return peso;
	}

	public double getVolumen() {
		return volumen;
	}

	public String getDestino() {
		return destino;
	}

	public boolean isNecesitaFrio() {
		return necesitaFrio;
	}

	public double isCostoTercerizFrio() {
		return costoTercerizFrio;
	}

	public void setCostoTercerizFrio(double costoTercerizFrio) {
		this.costoTercerizFrio = costoTercerizFrio;
	}

	@Override
	public String toString() {
		StringBuilder paq = new StringBuilder("Paquete " + codigoDelPaquete + ": ");
		paq.append("destino: " + destino).append(", peso: " + peso).append(", volumen: " + volumen).append(", necesita frío: " + necesitaFrio);
		return paq.append("\n").toString();
	}
}
