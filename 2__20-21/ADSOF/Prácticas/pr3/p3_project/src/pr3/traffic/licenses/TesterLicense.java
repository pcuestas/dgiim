package pr3.traffic.licenses;
import pr3.traffic.drivers.*;
import pr3.traffic.vehicles.*;

/**
 * Tester of the License class
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class TesterLicense {
	private void testYoungerThan18CannotHavePermitA() {
		Person ann = new Person("Ann Smith", 17);
		License c = new License(PermitKind.A);
		System.out.println("Test: YoungerThan18CannotHavePermitA");
		System.out.println(c);
		System.out.println(ann.setLicense(c));		// should return false, since Ann is not 18 years old		
	}
	
	private void testYoungerThan23CannotHavePermitC1() {
		Person ann = new Person("Ann Smith", 19);
		License c = new License(PermitKind.A, PermitKind.C1);
		System.out.println("=================\nTest: YoungerThan23CannotHavePermitC1");
		System.out.println(c);			
		System.out.println(ann.setLicense(c));		// should return false, since Ann is not 23 years old
	}
	
	private void testLicenseForVehicleKind() {
		Person ann = new Person("Ann Smith", 24);
		ann.setLicense(new License(PermitKind.A, PermitKind.C1));
		Car c = new Car("Fiat 500x", 2019, "1245 HYN", true, ann);
		System.out.println("=================\nTest: LicenseForVehicleKind");
		System.out.println(c);		// Ann is not the driver, since it has motorbyke and truck permits
		ann.getLicense().addPermit(PermitKind.B);
		c.setDriver(ann);
		System.out.println(c);		// Now she is
		System.out.println(ann.getLicense());		// license
	}
	
	private void testPrintVehicleWithNoOwner() {
		Person ann = new Person("Ann Smith", 24);
		ann.setLicense(new License(PermitKind.A, PermitKind.C1));
		Car c = new Car("Fiat 500x", 2019, "1245 HYN", true);
		System.out.println("=================\nTest: PrintVehicleWithNoOwner");
		System.out.println(c);		// Ann is not the driver nor the owner
		ann.getLicense().addPermit(PermitKind.B);
		c.setDriver(ann);
		System.out.println(c);		// Now she is only the driver
		System.out.println(ann.getLicense());		// license
	}
	
	private void testTryAddLicenseRejected() {
		Person ann = new Person("Ann Smith", 21);
		ann.setLicense(new License(PermitKind.A, PermitKind.B));
		Truck t = new Truck("TEST TRUCK", 2019, "1245 HYN", 2, ann);
		System.out.println("=================\nTest: testTryAddLicenseRejected");
		System.out.println(t);		// Ann is not the driver 
		ann.getLicense().addPermit(PermitKind.C1); // this should not be possible (returns false, bacause she is not 23 yet)
		System.out.println(t);		// Now she is still not the driver
		System.out.println(ann.getLicense());		// license
	}
	
	private void testLicenseForVehicleKind2() {
		Person ann = new Person("Ann Smith", 24);
		ann.setLicense(new License(PermitKind.A, PermitKind.C1));
		Car c = new Car("Fiat 500x", 2019, "1245 HYN", true, ann);
		System.out.println("=================\nTest: LicenseForVehicleKind2");
		System.out.println(c);		// Ann is not the driver, since it has only motorbyke and truck permits
		ann.getLicense().addPermit(PermitKind.B);
		System.out.println(c);		// Now she is
		System.out.println(ann.getLicense());		// license
	}
	
	private void testerLicenseForTwoDrivers() {
		Person ann = new Person("Ann Smith", 34);
		Person louise = new Person("Louise Smith", 34);
		License c = new License(PermitKind.A, PermitKind.C1);
		System.out.println("=================\nTest: testerLicenseForTwoDrivers");
		System.out.println(ann.setLicense(c) + ", " + louise.setLicense(c));// true, false
	}

	/**
	 * Entry point of the tester
	 * @param args not used
	 */
	public static void main(String[] args) {
		TesterLicense tap3 = new TesterLicense();
		tap3.testYoungerThan18CannotHavePermitA();
		tap3.testYoungerThan23CannotHavePermitC1();
		tap3.testLicenseForVehicleKind();
		tap3.testPrintVehicleWithNoOwner();
		tap3.testTryAddLicenseRejected();
		tap3.testLicenseForVehicleKind2();
		tap3.testerLicenseForTwoDrivers();
	}	
}
