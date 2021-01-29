import java.util.ArrayList;
import java.util.HashMap;

public class Empresa {

	private String nombre;
	private String cuit;
	private HashMap<String, Transporte> transportePorId;
	private ArrayList<Deposito> depositos;
	private HashMap<String, Viaje> viajeDeDestino;

	public Empresa(String cuit, String nombre) throws Exception {
		if (cuit == null || nombre == null || cuit == "" || nombre == "") {
			throw new Exception("el nombre y/o el cuit deben tener al menos un caracter");
		}
		this.nombre = nombre;
		this.cuit = cuit;
		transportePorId = new HashMap<String, Transporte>();
		depositos = new ArrayList<Deposito>();
		viajeDeDestino = new HashMap<String, Viaje>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Deposito> getDepositos() {
		return depositos;
	}

	public int agregarDeposito(double capacidad, boolean frigorifico, boolean propio) throws Exception {
		if (frigorifico && !propio) {
			throw new Exception(
					"ya que es un frigorífico tercerizado debe agregarse como tal teniendo en cuenta su costo por tonelada");
		}
		Deposito nuevo = new Deposito(capacidad, frigorifico, propio);
		depositos.add(nuevo);
		return nuevo.getNumeroDeDeposito();
	}

	public int agregarDepTercerizFrio(double capacidad, double costoPorTonelada) throws Exception {
		if (costoPorTonelada < 0) {
			throw new Exception("el costo por tonelada debe ser mayor a cero");
		}
		Deposito nuevo = new Deposito(capacidad, true, false);
		nuevo.setCostoPorTonelada(costoPorTonelada);
		depositos.add(nuevo);
		return nuevo.getNumeroDeDeposito();
	}

	public void agregarDestino(String destino, int km) {
		Viaje viaje = new Viaje(destino, km);
		viajeDeDestino.put(destino, viaje);
	}

	public void agregarTrailer(String idTransp, double cargaMax, double capacidad, boolean equipoFrio, double costoKm,
			double segCarga) {
		Trailer trailer = new Trailer(idTransp, cargaMax, capacidad, equipoFrio, costoKm, segCarga);
		transportePorId.put(idTransp, trailer);
	}

	public void agregarMegaTrailer(String idTransp, double cargaMax, double capacidad, boolean frigorifico,
			double costoKm, double segCarga, double costoFijo, double comida) {
		Megatrailer megatrailer = new Megatrailer(idTransp, cargaMax, capacidad, frigorifico, costoKm, segCarga,
				costoFijo, comida);
		transportePorId.put(idTransp, megatrailer);
	}

	public void agregarFlete(String idTransp, double cargaMax, double capacidad, double costoKm, int acomp,
			double costoPorAcom) {
		Flete flete = new Flete(idTransp, cargaMax, capacidad, costoKm, acomp, costoPorAcom);
		transportePorId.put(idTransp, flete);
	}

	public void asignarDestino(String idTransp, String destino) throws Exception {
		if (transportePorId.containsKey(idTransp) && viajeDeDestino.containsKey(destino)) {
			Transporte transporte = transportePorId.get(idTransp);
			int cantidadDeKilometros = viajeDeDestino.get(destino).getCantidadDeKilometros();
			if (!transporte.isEstaCargado() && transporte.validarTransporteParaCantKm(cantidadDeKilometros)) {
				transporte.setDestinoAsignado(destino);
			} else {
				throw new Exception(
						"el transporte ya se encuentra cargado o no esta habilitado para la cantidad de kilómetros");
			}
		} else {

			throw new Exception(
					"el transporte " + idTransp + " o el destino " + destino + " no pertenecen a la empresa");
		}
	}

	public boolean incorporarPaquete(String destino, double peso, double volumen, boolean necesitaFrio) {
		Paquete paquete = new Paquete(destino, peso, volumen, necesitaFrio);
		boolean seIncorporo = false;
		int indice = 0;
		while (!seIncorporo && indice < depositos.size()) {
			Deposito actual = depositos.get(indice);
			seIncorporo = actual.agregarPaquete(paquete);
			indice++;
		}
		return seIncorporo;
	}

	public double cargarTransporte(String idTransp) throws Exception {
		if (transportePorId.containsKey(idTransp)) {
			Transporte transporte = transportePorId.get(idTransp);
			if (transporte.getDestinoAsignado() == null) {
				throw new Exception("el transporte no tiene destino asignado");
			} else if (transporte.isEstaEnViaje()) {
				throw new Exception("el transporte se encuentra en viaje");
			} else {
				for (Deposito d : depositos) {
					d.cargarTransporte(transporte);
				}
				return transporte.volumenCargado();
			}
		} else {
			throw new Exception("el transporte " + idTransp + " no pertenece a la empresa");
		}
	}

	public void iniciarViaje(String idTransp) throws Exception {
		if (transportePorId.containsKey(idTransp)) {
			Transporte transporte = transportePorId.get(idTransp);
			if (transporte.getDestinoAsignado() == null) {
				throw new Exception("el transporte no tiene destino asignado.");
			} else if (transporte.isEstaEnViaje()) {
				throw new Exception("el transporte ya se encuentra en viaje.");
			} else if (!transporte.isEstaCargado() || transporte.volumenCargado() == 0) {
				throw new Exception("el transporte no se encuentra cargado.");
			} else {
				transporte.setEstaEnViaje(true);
			}
		} else {
			throw new Exception("el transporte " + idTransp + " no pertenece a la empresa");
		}
	}

	public void finalizarViaje(String idTransp) throws Exception {
		if (transportePorId.containsKey(idTransp)) {
			Transporte transporte = transportePorId.get(idTransp);
			if (transporte.isEstaEnViaje()) {
				transporte.descargarTransporte();

			} else {
				throw new Exception("el transporte no está en viaje");
			}
		} else {
			throw new Exception("el transporte " + idTransp + " no pertenece a la empresa");
		}
	}

	public double obtenerCostoViaje(String idTransp) throws Exception {
		if (transportePorId.containsKey(idTransp)) {
			Transporte transporte = transportePorId.get(idTransp);
			if (transporte.isEstaEnViaje()) {
				Viaje viaje = viajeDeDestino.get(transporte.getDestinoAsignado());
				return transporte.obtenerCostoViaje(viaje);
			} else {
				throw new Exception("el transporte debe estar en viaje");
			}
		} else {
			throw new Exception("el transporte " + idTransp + " no pertenece a la empresa");
		}
	}

	public String obtenerTransporteIgual(String idTransp) {
		if (transportePorId.containsKey(idTransp)) {
			Transporte transp = transportePorId.get(idTransp);
			for (Transporte t : transportePorId.values()) {
				if (transp.equals(t)) {
					return t.getIdentificacion();
				}
			}
		}
		return null;
	}

	public String toString() {
		StringBuilder empresa = new StringBuilder("\n \n");
		empresa.append("* Nombre *: " + nombre + "\n \n");
		empresa.append("* Cuit *: " + cuit).append("\n \n");
		empresa.append("* Depósitos *: \n \n");
		for (Deposito dep : depositos) {
			empresa.append(dep.toString() + "\n");
		}
		empresa.append("* Transportes *: \n");
		for (Transporte transp : transportePorId.values()) {
			empresa.append(transp.toString() + "\n");
		}
		empresa.append("\n \n *Viajes *: \n \n");
		for (Viaje viaje : viajeDeDestino.values()) {
			empresa.append(" Destino: " + viaje.getDestino() + ", cantidad de kilómetros: "
					+ viaje.getCantidadDeKilometros() + "\n \n");
		}
		return empresa.toString();
	}
}
