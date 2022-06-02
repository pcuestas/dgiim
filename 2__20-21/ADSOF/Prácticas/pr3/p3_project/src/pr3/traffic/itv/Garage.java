package pr3.traffic.itv;

import java.util.ArrayList;
import java.util.List;
import pr3.traffic.vehicles.Vehicle;

/**
 * Class Garage
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 */
public class Garage {
	private String name ; //name of garage
	private String address ;//address of garage
	private String province ;//province of garage
	private List<Vehicle> vehicles = new ArrayList<>(); //list of vehicles that have passed the itv in the garage
	
	/**
	 * Constructor of garage
	 * @param name name of garage
	 * @param address address of garage
	 * @param province province of garage
	 */
	public Garage(String name,String address, String province) {
		this.name = name;
		this.address = address;
		this.province = province;
	}

	/**
	 * The vehicles that have passed the itv in this garage
	 * @return the vehicles that have passed the itv in this garage
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	/**
	 * add a vehicle to the list of vehicles that have passed the itv in this garage
	 * @param v vehicle to add
	 */
	public void addVehicle(Vehicle v) {
		this.vehicles.add(v);
	}

	/**
	 * Getter for name
	 * @return name of garage
	 */
	public String getName() {
		return name;
	}

	/**
	 * toString method
	 */
	@Override
	public String toString() {
		return "Garage [name=" + name + ", address=" + address + ", province=" + province + "]";
	}
	
	
}
