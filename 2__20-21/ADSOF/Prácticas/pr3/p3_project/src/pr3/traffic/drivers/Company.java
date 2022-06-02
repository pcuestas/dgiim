package pr3.traffic.drivers;

/**
 * Company class (a owner of vehicles)
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class Company extends Owner {
	private Person responsible; // responsible of the company
	
	/**
	 * Constructor of Company
	 * @param name name of the company
	 * @param resp responsible of the company
	 */
	public Company(String name, Person resp) {
		super(name);
		this.responsible=resp;
	}
	
	/**
	 * Returns the default driver if this is the owner
	 * @return the default driver
	 */
	public Person getDefaultDriver() {
		return this.responsible;
	}
	/**
	 * Print the company information
	 */
	public String toString() {
		return this.getName()+" (responsible: "
				+this.responsible.getName()+")" 
				+super.toString();
	}
}
