package pr3.traffic.licenses;
import pr3.traffic.drivers.*;

/**
 * PermitKind enumeration
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 */
public enum PermitKind {
	
	/**For motorcycles*/
	A(18), 
	
	/**For cars*/
	B(18), 
	
	/**For Trucks*/
	C1(23); 
	

	private int minAge;	// minimum age
	
	/**
	 * Constructor of permit kind
	 * @param d min age
	 */
	private PermitKind(int d) {
		this.minAge = d;
	}
	
	/**
	 * Whether a person can have a permit (depending on age)
	 * @param p person 
	 * @return true iff the person can have the permit
	 */
	public boolean canHavePermit(Person p) {
		return p.getAge()>=this.minAge;
	}
}
