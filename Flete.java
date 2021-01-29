
public class Flete extends Transporte {

	private double costoPorAcompañante;
	private int cantidadDeAcompañantes;

	public Flete(String idTransp, double cargaMax, double capacMax, double costoKm, int acomp, double costoAcomp) {
		super(idTransp, cargaMax, capacMax, costoKm);
		if(costoAcomp < 0 || acomp < 0) {
			throw new RuntimeException("el costo por acompañante y el número de acompañantes no pueden ser menores a cero");
		}
		costoPorAcompañante = costoAcomp;
		cantidadDeAcompañantes = acomp;
	}

	public double getCostoPorAcompañante() {
		return costoPorAcompañante;
	}

	public int getCantidadDeAcompañantes() {
		return cantidadDeAcompañantes;
	}

	@Override
	boolean validarTransporteParaCantKm(int cantKm) {
		return true;
	}

	@Override
	double obtenerCostoViaje(Viaje viaje) {
		double costoTercerizFrio = getCostoAdicTercerizFrio();
		return viaje.getCantidadDeKilometros() * getCostoPorKilometro() + costoPorAcompañante * cantidadDeAcompañantes + costoTercerizFrio;
	}

	@Override
	boolean validarTransporteParaPaquete(Paquete paquete) {
		return getCargaDisponible() > paquete.getPeso() && getCapacidadDisponible() > paquete.getVolumen()
				&& !paquete.isNecesitaFrio();
	}

	public String toString() {
		StringBuilder transp = new StringBuilder();
		transp.append(super.toString());
		transp.append(" costoPorAcompañante: " + costoPorAcompañante);
		transp.append("\n cantidadDeAcompañantes: " + cantidadDeAcompañantes);
		if (!paquetes.isEmpty()) {
			transp.append("\n").append(" Este transporte lleva la siguiente mercadería: ");
			for (Paquete p : paquetes) {
				transp.append("Paquete número: ").append(p.getCodigoDelPaquete() + "  ");
			}
		}
		return transp.toString();
	}

}
