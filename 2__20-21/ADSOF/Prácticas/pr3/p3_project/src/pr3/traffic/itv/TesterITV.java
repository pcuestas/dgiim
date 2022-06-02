package pr3.traffic.itv;

import pr3.traffic.vehicles.*;
import java.time.*;

/**
 * TesterITV class for the test of the ITV
 * 
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, 
 *         Diego Cid: diego.cid@estudiante.uam.es
 * 
 */
public class TesterITV {	
	private static Garage garage1 = new Garage("garage1", "address1", "Madrid");
	private static Garage garage2 = new Garage("garage2", "address2", "Madrid");

	private void testerCarMotorcycleITV() {
		Vehicle fleet[] = { new Car("Fiat 500x", 2018, "1245 HYN", true), // less than 4 years
				new Car("Aar2", 2012, "5643 KOI", false), // 4-10 years
				new Car("Car3", 2003, "0987 ETG", false), // more than 10 years
				new Motorcycle("Mot1", 2011, "5643 KDF", false), // 4-10 years
				new Motorcycle("Mot2", 2009, "0987 ETY", false) }; // more than 10 years
		System.out.println("-----------TesterCarMotorcycleITV");
		for (Vehicle c : fleet)
			System.out.printf("%b ", c.itvStatus());// first ok, the rest false
		System.out.println();

		LocalDate d2018 = LocalDate.of(2018, 8, 1);
		fleet[1].addITV(new ITV(d2018, garage1, "all ok"));
		fleet[2].addITV(new ITV(d2018, garage1, "all ok"));
		System.out.println(fleet[1].itvStatus() + ", " + fleet[2].itvStatus());// false

		LocalDate d2019 = LocalDate.of(2019, 8, 3);
		fleet[1].addITV(new ITV(d2019, garage1, "all ok"));// now true
		fleet[2].addITV(new ITV(d2019, garage1, "all ok"));// still false
		fleet[3].addITV(new ITV(d2019, garage1, "all ok"));// true
		System.out.println(fleet[1].itvStatus() + ", " + fleet[2].itvStatus() + ", " + fleet[3].itvStatus());

		LocalDate d2020 = LocalDate.of(2020, 7, 3);
		LocalDate d2021 = LocalDate.of(2021, 1, 23);
		for (Vehicle c : fleet) {
			c.addITV(new ITV(d2020, garage1, "all ok"));// all ok
			System.out.printf("%b-", c.itvStatus());
			c.addITV(new ITV(d2021, garage1, "all ok"));// all ok
			System.out.printf("%b|", c.itvStatus());
		}
	}

	private void testerTruckITV() {
		Vehicle fleet[] = { new Truck("t0", 2020, "1245 HYN", 2), // less than 2 years
				new Truck("t1", 2018, "5643 KOI", 2), // 2-6 years
				new Truck("t2", 2015, "0987 ETG", 3), // 2-6 years
				new Truck("t3", 2011, "5643 KDF", 4), // 6-10 years
				new Truck("t4", 2009, "0987 ETY", 3) }; // more than 10 years
		System.out.println("\n-----------TesterTruckITV");
		for (Vehicle c : fleet)
			System.out.printf("%b ", c.itvStatus());// first ok, the rest false
		System.out.println();

		LocalDate d2018 = LocalDate.of(2018, 7, 2);
		for (Vehicle c : fleet) {
			if(c.getPurchaseYear() == 2020) {
				continue; 
			}				
			c.addITV(new ITV(d2018, garage2, "all ok"));
			System.out.printf("%b ", c.itvStatus());// 4 false
		}
		System.out.println();

		LocalDate d2019 = LocalDate.of(2019, 8, 3);
		for (Vehicle c : fleet) {
			if(c.getPurchaseYear() < 2020) {
				c.addITV(new ITV(d2019, garage2, "all ok"));
			}
			System.out.printf("%b ", c.itvStatus()); // first 3 ok (2 years ago)
		}
		System.out.println();

		LocalDate d2020 = LocalDate.of(2020, 7, 12);
		for (Vehicle c : fleet) {
			c.addITV(new ITV(d2020, garage2, "all ok"));// all ok except the last
			System.out.printf("%b ", c.itvStatus());
		}
		System.out.println();

		LocalDate dLast = LocalDate.of(2020, 8, 23);// more than 6 months ago
		fleet[4].addITV(new ITV(dLast, garage2, "all ok"));
		String aux = "" + fleet[4].itvStatus();
		dLast = LocalDate.of(2020, 12, 29);// less than 6 months ago
		fleet[4].addITV(new ITV(dLast, garage2, "all ok"));
		aux += " " + fleet[4].itvStatus();
		System.out.println(aux);
	}
	/**
	 * Entry point of the tester
	 * @param args not used
	 */
	public static void main(String[] args) {
		TesterITV tester = new TesterITV();
		tester.testerCarMotorcycleITV();
		tester.testerTruckITV();

		System.out.println("Vehicles that have passed the ITV in each garage:\n------------ garage1:");
		for (Vehicle v : garage1.getVehicles()) {
			System.out.println(v);
		}
		System.out.println("------------ garage2:");
		for (Vehicle v : garage2.getVehicles()) {
			System.out.println(v);
		}
	}

}
