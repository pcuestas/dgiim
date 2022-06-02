package pr3.traffic.vehicles;
import pr3.traffic.drivers.*;
import pr3.traffic.licenses.*;

/**
 * First tester of p3
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class TesterPr31 {
	/**
	 * Entry point of the tester
	 * @param args not used
	 */
	public static void main(String[] args) {
		Person ann = new Person("Ann Smith", 30);		 		 			
		Person louise = new Person("Louise Lane", 17);
		Person anthony = new Person("Anthony Johnson", 27);
		Company fdinc = new Company("Fast Delivery Inc", ann);	// Ann is responsible for FDINC
		
		// assign license so that it can work with the changes made in next sections
		ann.setLicense(new License(PermitKind.A, PermitKind.B, PermitKind.C1));
		louise.setLicense(new License(PermitKind.A, PermitKind.B, PermitKind.C1));
		anthony.setLicense(new License(PermitKind.A, PermitKind.B, PermitKind.C1));

		
		Vehicle fleet[] = { 
				new Car("Fiat 500x", 2019, "1245 HYN", true, ann), // Ann's car, who drives it		
				new Truck("IvecoDaily", 2010, "5643 KOI", 2, fdinc), // Truck of FDINC
				new Motorcycle("Harley Davidson", 2003, "0987 ETG", false)};
		
		fleet[2].setOwner(ann);
		// Method setOwner should allow receiving a Company: fleet[2].setOwner(fdinc);
		System.out.println("Can Louise drive a Harley? "+fleet[2].setDriver(louise));
		// Louise, being less than 18 years old, cannot be the driver			
		
		for (Vehicle v : fleet ) {
			System.out.println(v);
			System.out.println("------------------------");
		}
		
		fleet[2].setDriver(anthony);
		System.out.println(fleet[2]);
		System.out.println("People:");
		System.out.println(anthony+"\n---");				
		System.out.println(ann+"\n---");
		System.out.println("Company:");
		System.out.println(fdinc);
	}
}
