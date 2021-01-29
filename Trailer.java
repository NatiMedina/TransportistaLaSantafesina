
public class Trailer extends Transporte {

	private double seguroDeCarga;
	static int limiteMaximoDeViaje = 500;
	private boolean tieneRefrigeracion;

	public Trailer(String ident, double cargaMax, double capacMax, boolean frigorifico, double costoKm,
			double segCarga) {
		super(ident, cargaMax, capacMax, costoKm);
		if(segCarga <= 0) {
			throw new RuntimeException("el seguro de carga debe ser mayor a 0");
		}
		seguroDeCarga = segCarga;
		tieneRefrigeracion = frigorifico;
	}

	public double getSeguroDeCarga() {
		return seguroDeCarga;
	}


	public int getLimiteMaximoDeViaje() {
		return limiteMaximoDeViaje;
	}


	public boolean isTieneRefrigeracion() {
		return tieneRefrigeracion;
	}


	@Override
	public double obtenerCostoViaje(Viaje viaje) {
		double costoTercerizFrio = getCostoAdicTercerizFrio();
		return viaje.getCantidadDeKilometros() * getCostoPorKilometro() + seguroDeCarga + costoTercerizFrio;
	}

	@Override
	public boolean validarTransporteParaCantKm(int KmViaje) {
		return (KmViaje < limiteMaximoDeViaje);
	}

	@Override
	boolean validarTransporteParaPaquete(Paquete paquete) {
		return getCargaDisponible() > paquete.getPeso() && getCapacidadDisponible() > paquete.getVolumen()
				&& tieneRefrigeracion == paquete.isNecesitaFrio();
	}

	public String toString() {
		StringBuilder transp = new StringBuilder();
		transp.append(super.toString());
		transp.append(" seguroDeCarga: " + seguroDeCarga);
		transp.append("\n limiteMaximoDeViaje: 500");
		transp.append("\n tieneRefrigeracion: " + tieneRefrigeracion);
		if (!paquetes.isEmpty()) {
			transp.append("\n").append(" Este transporte lleva la siguiente mercadería: ");
			for (Paquete p : paquetes) {
				transp.append("Paquete número: ").append(p.getCodigoDelPaquete() + "  ");
			}
		}
		return transp.toString();
	}

}
