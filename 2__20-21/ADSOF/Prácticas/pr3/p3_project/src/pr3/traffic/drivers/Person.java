package pr3.traffic.drivers;
import pr3.traffic.licenses.*;

/**
 * Person class (a owner or diriver of vehicles)
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class Person extends Owner {
	private int age; //age of the person
	private License license; // driving license
	
	/**
	 * Constructor of Person
	 * @param name name of the person
	 * @param age age of the person
	 */
	public Person(String name, int age) {
		super(name);
		this.age=age;
	}
	
	/**
	 * Get age of person
	 * @return the age
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * Returns the default driver if this is the owner
	 * @return the default driver
	 */
	public Person getDefaultDriver() {
		return this;
	}
	
	/**
	 * Print the person information
	 */
	public String toString() {
		return this.getName()+super.toString();
	}
	
	/**
	 * Sets the license of the person
	 * @param c the license
	 * @return true if there were no errors (false if person can not have the permits in the license)
	 */
	public boolean setLicense(License c) {
		if(c.setOwner(this)) {
			this.license = c;
			return true;
		}else {
			return false;
		}		
	}
	
	/**
	 * Getter for license
	 * @return the license of the client
	 */
	public License getLicense() {
		return this.license;
	}
	
}
