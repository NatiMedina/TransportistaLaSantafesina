
public class Megatrailer extends Transporte {

	private double seguroDeCarga;
	static int limiteMinimoDeviaje = 500;
	private double costoFijoDeViaje;
	private double gastoDeComida;
	private boolean tieneRefrigeracion;

	public Megatrailer(String ident, double cargaMax, double capacMax, boolean equipoFrio, double costoKm,
			double seguro, double costoFijo, double comida) {
		super(ident, cargaMax, capacMax, costoKm);
		if(costoFijo < 0 || comida < 0 || seguro < 0) {
			throw new RuntimeException("el costo fijo, la comida y el seguro no pueden ser negativos");
		}
		costoFijoDeViaje = costoFijo;
		gastoDeComida = comida;
		tieneRefrigeracion = equipoFrio;
		seguroDeCarga = seguro;
	}

	public double getSeguroDeCarga() {
		return seguroDeCarga;
	}


	public static double getLimiteMinimoDeviaje() {
		return limiteMinimoDeviaje;
	}

	public double getCostoFijoDeViaje() {
		return costoFijoDeViaje;
	}

	public double getGastoDeComida() {
		return gastoDeComida;
	}

	public boolean isTieneRefrigeracion() {
		return tieneRefrigeracion;
	}

	@Override
	double obtenerCostoViaje(Viaje viaje) {
		double costoTercerizFrio = getCostoAdicTercerizFrio();
		return viaje.getCantidadDeKilometros() * getCostoPorKilometro() + seguroDeCarga + costoFijoDeViaje + gastoDeComida + costoTercerizFrio;
	}

	@Override
	public boolean validarTransporteParaCantKm(int cantKm) {
		return cantKm >= Megatrailer.limiteMinimoDeviaje;

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
		transp.append("\n limiteMinimoDeviaje: 500");
		transp.append("\n costoFijoDeViaje: " + costoFijoDeViaje);
		transp.append("\n gastoDeComida: " + gastoDeComida);
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
