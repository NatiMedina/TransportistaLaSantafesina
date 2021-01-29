
public class Pruebas {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		double cargado;
		Empresa e = new Empresa("30112223334​","La Santafesina​");
//		System.out.println(e.toString());
		e.agregarDepTercerizFrio(40000, 10);
		e.agregarDeposito(50000, true, true);
		e.agregarDeposito(80000, false, true);
		e.agregarDeposito(90000, false, false);
		e.agregarDestino("Rosario", 100);
		e.agregarDestino("Buenos Aires", 400);
		e.agregarDestino("Mar del Plata", 800);
		e.agregarTrailer("AA333XQ​", 10000, 60, true, 2, 100);
		e.agregarMegaTrailer("AA444PR​", 15000, 100, false, 3, 150, 200, 50);
		e.agregarFlete("AB555MN​", 5000, 20, 4, 2, 300);
		e.asignarDestino("AA333XQ​","Buenos Aires");
		e.asignarDestino("AB555MN​","Rosario");
		e.incorporarPaquete("Buenos Aires", 100, 2, true);
		e.incorporarPaquete("Buenos Aires", 150, 1, true);
		e.incorporarPaquete("Mar del Plata​", 100, 2, true);
		e.incorporarPaquete("Mar del Plata​", 150, 1, true);
		e.incorporarPaquete("Rosario", 100, 2, true);
		e.incorporarPaquete("Rosario", 150, 1, true);
		e.incorporarPaquete("Buenos Aires", 200, 3, false);
		e.incorporarPaquete("Buenos Aires", 400, 4, false);
		e.incorporarPaquete("Mar del Plata​", 200, 3, false);
		e.incorporarPaquete("Rosario", 80, 2, false);
		e.incorporarPaquete("Rosario", 250, 2, false);
		cargado = e.cargarTransporte("AA333XQ​");
		System.out.println("Se cargaron ​" + cargado +" metros cubicos en el transp AA333XQ​");
		e.iniciarViaje("AA333XQ​");
		System.out.println("Costo del viaje: ​" + e.obtenerCostoViaje("AA333XQ​"));
//		System.out.println(e.toString());
//		e.finalizarViaje("AA333XQ​");
		System.out.println(e.toString());

	}

}
