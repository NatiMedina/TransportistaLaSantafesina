import java.util.ArrayList;

public abstract class Transporte {

	private String identificacion;
	private double cargaMaxima;
	private double capacidadMaxima;
	private double cargaDisponible;
	private double capacidadDisponible;
	private double costoPorKilometro;
	private boolean estaCargado;
	private boolean estaEnViaje;
	private String destinoAsignado;
	private double costoAdicTercerizFrio;
	protected ArrayList<Paquete> paquetes;

	public Transporte(String idTransp, double cargaMax, double capacMax, double costoKm) {
		if (idTransp == null || idTransp == "") {
			throw new RuntimeException("la identificación no puede no contener ningún caracter o ser un espacio");
		}
		if (cargaMax < 0 || capacMax < 0) {
			throw new RuntimeException("la carga y la capacidad no pueden ser negativos");
		}
		if (costoKm < 0) {
			throw new RuntimeException("el costo no puede ser negativo");
		}
		identificacion = idTransp;
		cargaMaxima = cargaMax;
		capacidadMaxima = capacMax;
		cargaDisponible = cargaMax;
		capacidadDisponible = capacMax;
		costoPorKilometro = costoKm;
		estaCargado = false;
		estaEnViaje = false;
		destinoAsignado = null;
		costoAdicTercerizFrio = 0.00;
		paquetes = new ArrayList<Paquete>();

	}

	public ArrayList<Paquete> getPaquetes() {
		return paquetes;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public double getCargaMaxima() {
		return cargaMaxima;
	}

	public double getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public double getCapacidadDisponible() {
		return capacidadDisponible;
	}

	public void setCapacidadDisponible(double capacidadDisponible) {
		if (capacidadDisponible > 0 && capacidadDisponible <= capacidadMaxima) {
			this.capacidadDisponible = capacidadDisponible;
		}
	}

	public double getCargaDisponible() {
		return cargaDisponible;
	}

	public void setCargaDisponible(double cargaDisponible) {
		if (cargaDisponible > 0 && cargaDisponible <= cargaMaxima) {
			this.cargaDisponible = cargaDisponible;
		}
	}

	public double getCostoPorKilometro() {
		return costoPorKilometro;
	}


	public boolean isEstaCargado() {
		return estaCargado;
	}

	public void setEstaCargado(boolean estaCargado) {
		this.estaCargado = estaCargado;
	}

	public boolean isEstaEnViaje() {
		return estaEnViaje;
	}

	public void setEstaEnViaje(boolean estaEnViaje) {
		this.estaEnViaje = estaEnViaje;
	}

	public String getDestinoAsignado() {
		return destinoAsignado;
	}

	public void setDestinoAsignado(String destinoAsignado) {
		if (destinoAsignado != null && destinoAsignado != "") {
			this.destinoAsignado = destinoAsignado;
		}
	}

	public double getCostoAdicTercerizFrio() {
		return costoAdicTercerizFrio;
	}

	public void setCostoAdicTercerizFrio(double costoAdicTercerizFrio) {
		if (costoAdicTercerizFrio > 0) {
			this.costoAdicTercerizFrio = costoAdicTercerizFrio;
		}
	}

	public double pesoCargado() {
		return cargaMaxima - cargaDisponible;
	}

	public double volumenCargado() {
		return capacidadMaxima - capacidadDisponible;
	}

	private boolean igualDestino(Transporte transp) {
		return destinoAsignado == transp.getDestinoAsignado();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transporte other = (Transporte) obj;
		return igualDestino(other) && 
				volumenCargado() == other.volumenCargado() && 
				pesoCargado() == other.pesoCargado();
	}

	public void cargarTransporte(Paquete paquete) {
		setCapacidadDisponible(capacidadDisponible - paquete.getVolumen());
		setCargaDisponible(cargaDisponible - paquete.getPeso());
		setCostoAdicTercerizFrio(getCostoAdicTercerizFrio() + paquete.isCostoTercerizFrio());
		setEstaCargado(true);
		paquetes.add(paquete);
	}

	public void descargarTransporte() {

		setCapacidadDisponible(capacidadMaxima);
		setCargaDisponible(cargaMaxima);
		setEstaCargado(false);
		setEstaEnViaje(false);
		setDestinoAsignado(null);
		setCostoAdicTercerizFrio(0.00);
		paquetes.clear();
	}

	public String toString() {
		StringBuilder transp = new StringBuilder();
		transp.append(" \n Identificación: " + identificacion);
		transp.append(" \n cargaMaxima: " + cargaMaxima);
		transp.append(" \n capacidadMaxima: " + capacidadMaxima);
		transp.append(" \n cargaDisponible: " + cargaDisponible);
		transp.append(" \n capacidadDisponible: " + capacidadDisponible);
		transp.append(" \n costoPorKilometro: " + costoPorKilometro);
		transp.append(" \n estaCargado: " + estaCargado);
		transp.append(" \n estaEnViaje: " + estaEnViaje);
		transp.append(" \n destinoAsignado: " + destinoAsignado);
		transp.append(" \n costoAdicTercerizFrio: " + costoAdicTercerizFrio);

		return transp.append("\n").toString();
	}

	abstract double obtenerCostoViaje(Viaje viaje);

	abstract boolean validarTransporteParaCantKm(int cantKm);

	abstract boolean validarTransporteParaPaquete(Paquete paquete);

}
