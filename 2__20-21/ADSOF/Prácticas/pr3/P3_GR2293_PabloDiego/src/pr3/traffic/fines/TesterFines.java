package pr3.traffic.fines;
import pr3.traffic.drivers.*;
import pr3.traffic.itv.*;
import pr3.traffic.licenses.*;
import pr3.traffic.vehicles.*;
import java.util.*;
import java.time.*;

/**
 * Test the Fine and FineReader classes
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es), 
 *         Diego Cid (diego.cid@estudiante.uam.es)
 */
public class TesterFines {
	private static void testerNoDriverNoITV() {
		Person ann = new Person("Ann Smith", 30);		 		// Ann 
		Person anthony = new Person("Anthony Johnson", 12); // can not drive, so will have no license and can not be driver
		Company fdinc = new Company("Fast Delivery Inc", ann);	// Ann is responsible for FDINC
		
		ann.setLicense(new License(PermitKind.B, PermitKind.C1));
		anthony.setLicense(new License(PermitKind.A));//will fail, he's 12
		
		System.out.println("============== testerNoDriverNoITV");
		
		Vehicle fleet[] = { 
				new Car("Fiat 500x", 2019, "1245 HYN", true, ann), 	// Ann's car, who drives it - ITV ok
				new Truck("IvecoDaily", 2010, "5643 KOI", 2, fdinc), // FDINC's car
				new Motorcycle("Harley Davidson", 2003, "0987 ETG", false, anthony)};
		
		FineProcessor pm = new FineProcessor(Arrays.asList(fleet));
		pm.process(FineReader.read("txt/fines_radar1.txt"));
	}
	
	private static void testerNoDriverWithITV() {
		Person ann = new Person("Ann Smith", 30);		 		// Ann 
		Person anthony = new Person("Anthony Johnson", 12); // can not drive, so will have no license and can not be driver
		Company fdinc = new Company("Fast Delivery Inc", ann);	// Ann is responsible for FDINC
		Garage g = new Garage("garage2", "address2", "Madrid");
		
		ann.setLicense(new License(PermitKind.B, PermitKind.C1));
		anthony.setLicense(new License(PermitKind.A));
		
		System.out.println("============== testerNoDriverWithITV");
		
		Vehicle fleet[] = { 
				new Car("Fiat 500x", 2019, "1245 HYN", true, ann), 	// Ann's car, who drives it		
				new Truck("IvecoDaily", 2010, "5643 KOI", 2, fdinc), // FDINC's car
				new Motorcycle("Harley Davidson", 2003, "0987 ETG", false, anthony)};
		fleet[0].addITV(new ITV(LocalDate.of(2021, 3, 12), g, "no comments"));
		fleet[1].addITV(new ITV(LocalDate.of(2021, 3, 12), g, "no comments"));
		fleet[2].addITV(new ITV(LocalDate.of(2021, 3, 12), g, "no comments"));
		
		FineProcessor pm = new FineProcessor(Arrays.asList(fleet));
		pm.process(FineReader.read("txt/fines_radar1.txt"));
	}
	
	
	private static void testerNoITVsExpired(){
		Person ann = new Person("Ann Smith", 30);		 		// Ann 			
		Person anthony = new Person("Anthony Johnson", 27);
		Company fdinc = new Company("Fast Delivery Inc", ann);	// Ann is responsible for FDINC
		Garage g = new Garage("garage2", "address2", "Madrid");
		
		ann.setLicense(new License(PermitKind.B, PermitKind.C1));
		anthony.setLicense(new License(PermitKind.A));
		
		System.out.println("============== testerNoITVsExpired");
		
		Vehicle fleet[] = { 
				new Car("Fiat 500x", 2019, "1245 HYN", true, ann), 	// Ann's car, who drives it		
				new Truck("IvecoDaily", 2010, "5643 KOI", 2, fdinc), // FDINC's car
				new Motorcycle("Harley Davidson", 2003, "0987 ETG", false, anthony)};
		
		fleet[1].addITV(new ITV(LocalDate.of(2021, 3, 12), g, "no comments"));
		fleet[2].addITV(new ITV(LocalDate.of(2020, 12, 12), g, "no comments"));
		
		FineProcessor pm = new FineProcessor(Arrays.asList(fleet));
		pm.process(FineReader.read("txt/fines_radar1.txt"));
	}
	
	/**
	 * Entry point of the tester
	 * @param args not used
	 */
	public static void main(String[] args) {
		Person ann = new Person("Ann Smith", 30);		 		// Ann 			
		Person anthony = new Person("Anthony Johnson", 27);
		Company fdinc = new Company("Fast Delivery Inc", ann);	// Ann is responsible for FDINC
		
		ann.setLicense(new License(PermitKind.B, PermitKind.C1));
		anthony.setLicense(new License(PermitKind.A));
		
		Vehicle fleet[] = { 
				new Car("Fiat 500x", 2019, "1245 HYN", true, ann), 	// Ann's car, who drives it		
				new Truck("IvecoDaily", 2010, "5643 KOI", 2, fdinc), // FDINC's car
				new Motorcycle("Harley Davidson", 2003, "0987 ETG", false, anthony)};
		
		FineProcessor pm = new FineProcessor(Arrays.asList(fleet));
		pm.process(FineReader.read("txt/fines_radar1.txt"));
		
		testerNoDriverNoITV();// last two vehicles have expired ITVs
		testerNoDriverWithITV();// no driver, but all ITVs are fine
		testerNoITVsExpired();// no ITVs expired--just like in the instructions pdf
	}
}
