package pr3.traffic.drivers;
import pr3.traffic.vehicles.*;
import java.util.*;

/**
 * This abstract class is superclass of classes that can be owners of a Vehicle
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public abstract class Owner {
	private String name;//name of the owner
	private List<Vehicle> vehicles = new ArrayList<>(); //vehicles owned
	
	/**
	 * Constructor of the abstract class Owner
	 * @param n name of the Owner
	 */
	public Owner(String n){
		this.name=n;
	}
	
	/**
	 * Add a vehicle to the collection of vehicles owned by this owner
	 * @param v the vehicle to add
	 */
	public void addVehicle(Vehicle v) {
		this.vehicles.add(v);
	}
	
	/**
	 * Returns the default driver if this is the owner
	 * @return the default driver
	 */
	public abstract Person getDefaultDriver();
	
	/**
	 * The name of the owner
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Print the owner information
	 */
	@Override public String toString() {
		if (this.vehicles.size()==0) {
			return "";
		}
		String s = " owner of:";
		for (Vehicle v: vehicles ) {
			s += "\n"+v;
		}
		return s;
	}
}
