package pr3.traffic.licenses;
import java.util.*;
import pr3.traffic.drivers.*;

/**
 * Class License
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es), 
 *         Diego Cid (diego.cid@estudiante.uam.es)
 */
public class License {
	private static int count = 0; //to keep track of identifiers

	private int id; //identifier
	private Set<PermitKind> permits = new TreeSet<>(); //permits
	private int points = 12; //points-initially 12
	private Person owner; //owner of the License
	
	/**
	 * Constructor of license
	 * 
	 * @param kinds permits of the license
	 */
	public License(PermitKind... kinds) {
		this.id = count;
		count += 1;

		for (PermitKind k : kinds) {
			this.permits.add(k);
		}
	}
	
	/**
	 * The permits of the license
	 * 
	 * @return the set of permits of the license
	 */
	public Set<PermitKind> getPermits() {
		return this.permits;
	}

	/**
	 * Set the owner of the License
	 * 
	 * @param p owner of the license
	 * @return true iff the person can be the owner
	 */
	public boolean setOwner(Person p) {
		if(this.owner != null) {// there can not be more owners if
			return false; 	    // this license has one owner already
		}
		for (PermitKind k : this.permits) {
			if (!(k.canHavePermit(p))) {
				return false;
			}
		}
		this.owner = p;
		return true;
	}
	
	/**
	 * Adds a permit kind to the license
	 * 
	 * @param k permit kind
	 * @return false iff the owner cannot have this permit
	 */
	public boolean addPermit(PermitKind k) {
		if (this.owner != null && !(k.canHavePermit(this.owner))) {
			return false;
		}
		this.permits.add(k);
		return true;
	}

	/**
	 * Subtracts points and returns remaining points or -1 if there were already 0
	 * points
	 * 
	 * @param p points to subtract
	 * @return number of points remaining or -1 if there were already 0 points
	 */
	public int subtractPoints(int p) {
		if (this.points == 0) {
			return -1;
		} else {
			this.points -= p;
			if (this.points < 0) {
				this.points = 0;
			}
			return this.points;
		}
	}

	/**
	 * Prints the info of the License
	 */
	@Override
	public String toString() {
		return "License [id=" + id + ", permits=" + permits + ", points=" + points + "]";
	}
	
	
}
