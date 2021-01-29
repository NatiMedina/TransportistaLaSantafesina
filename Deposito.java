import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Deposito {
	private int numeroDeDeposito;
	private HashMap<String, ArrayList<Paquete>> paquetesPorDestino;
	private boolean tieneRefrigeracion;
	private boolean esPropio;
	private double capMaxima;
	private double capacidadDisponible;
	static private int numero = 0;
	private double costoPorTonelada;

	public Deposito(double capacidad, boolean frio, boolean propio) {
		if(capacidad <= 0) {
			throw new RuntimeException("la capacidad debe ser mayor a 0");
		}
		numeroDeDeposito = ++numero;
		paquetesPorDestino = new HashMap<String, ArrayList<Paquete>>();
		tieneRefrigeracion = frio;
		esPropio = propio;
		capMaxima = capacidad;
		capacidadDisponible = capacidad;
		costoPorTonelada = 0.00;
	}

	public int getNumeroDeDeposito() {
		return numeroDeDeposito;
	}

	public boolean isTieneRefrigeracion() {
		return tieneRefrigeracion;
	}

	public boolean esPropio() {
		return esPropio;
	}

	public double getCapMaxima() {
		return capMaxima;
	}

	public double getCapacidadDisponible() {
		return capacidadDisponible;
	}

	public void setCapacidadDisponible(double capacidadDisponible) {
		this.capacidadDisponible = capacidadDisponible;
	}

	public double getCostoPorTonelada() {
		return costoPorTonelada;
	}

	public void setCostoPorTonelada(double costoPorTonelada) {
		this.costoPorTonelada = costoPorTonelada;
	}

	public boolean agregarPaquete(Paquete paquete) {
		if (capacidadDisponible > paquete.getVolumen() && tieneRefrigeracion == paquete.isNecesitaFrio()) {
			if (!paquetesPorDestino.containsKey(paquete.getDestino())) {
				paquetesPorDestino.put(paquete.getDestino(), new ArrayList<Paquete>());
			}
			paquetesPorDestino.get(paquete.getDestino()).add(paquete);
			setCapacidadDisponible(capacidadDisponible - paquete.getVolumen());
			return true;
		}
		return false;
	}

	private void retirarPaquete(Paquete paquete) {
		setCapacidadDisponible(capacidadDisponible + paquete.getVolumen());
		if (!esPropio && tieneRefrigeracion) {
			paquete.setCostoTercerizFrio(paquete.getPeso() * costoPorTonelada / 1000);
		}
	}

	public void cargarTransporte(Transporte transporte) {
		if (paquetesPorDestino.containsKey(transporte.getDestinoAsignado())) {
			Iterator<Paquete> p = paquetesPorDestino.get(transporte.getDestinoAsignado()).iterator();
			Paquete paq;
			while (p.hasNext()) {
				paq = p.next();
				if (transporte.validarTransporteParaPaquete(paq)) {
					retirarPaquete(paq);
					transporte.cargarTransporte(paq);
					p.remove();
				}
			}
		}
	}

	public String toString() {
		StringBuilder dep = new StringBuilder();
		dep.append("Depósito nro " + numeroDeDeposito +":");
		if (paquetesPorDestino.isEmpty()) {
			return dep.append(" este depósito se encuentra vacío. \n \n").toString();
		} else {
			dep.append("\n \n");
			for (ArrayList<Paquete> p : paquetesPorDestino.values()) {
				for (Paquete paq : p) {
					dep.append(paq.toString()).append("\n");
				}
			}
			dep.append("\n");
			return dep.toString();
		}
	}
}