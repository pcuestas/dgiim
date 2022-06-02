package pr3.traffic.vehicles;

import java.time.LocalDate;
import java.util.*;
import pr3.traffic.drivers.*;
import pr3.traffic.itv.ITV;
import pr3.traffic.licenses.*;

/**
 * Abstract class Vehicle that stores model name and year
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 * 
 **/
public abstract class Vehicle {	
	private String model;//model of the vehicle
	private int purchaseYear;//purchase year of vehicle
	private String plate;//plate of vehicle
	private Owner owner; //owner of vehicle - optional
	private Person driver; //driver of vehicle optional
	private List<ITV> itvs; //list of passed ITVs(ordered)
	
	/**
	 * Array in the form: {{a1,b1},{a2,b2},{a3,b3}, ..., {an,bn}}.
     * Where a1 < a2 < a3 < ... < an. 
	 * Used to calculate the remaining years until the next ITV. 
	 * Meaning: if the age (in years) of the vehicle is lower than a1, 
	 *              it has to pass no inspection,
	 *          if the age of the vehicle is between 
	 * 				a1 and a2, then it has to pass the ITV every b1 months,
	 *          if the age of the vehicle is between 
	 * 				a2 and a3, then it has to pass the ITV every b2 months,
	 *          if the age of the vehicle is between 
	 * 				a3 and a4, then it has to pass the ITV every b3 months, ...
	 *  		... if the age is higher than an, it has to pass the ITV every bn months
	 **/
	private static final long[][] itvTimeArray = new long[][] {{4, 2*12}, {10, 1*12}};
	
	/**
	 * Constructor of Vehicle
	 * @param mod : the model string
	 * @param a : year
	 * @param pl : plate
	 * @param o : owner
	 * */
	public Vehicle(String mod, int a, String pl, Owner o) {		
		this.model = mod;
		this.purchaseYear = a;
		this.plate = pl;
		this.itvs = new ArrayList<>();
		if(o!=null) {
			this.setOwner(o);
		}
	}
	/**
	 * Constructor of Vehicle
	 * @param mod : the model string
	 * @param a : year
	 * @param pl : plate
	 * */
	public Vehicle(String mod, int a, String pl) {
		this(mod, a, pl, null);
	}
	
	/**
	 * Converts content of the object to string
	 */
	@Override public String toString() {
		Person d;
		return "model "+this.model+", number plate: "+this.plate
				+", purchase year "+this.purchaseYear
				+", with "+this.numWheels()+" wheels, index:"
				+this.getPollutionIndex()
				+", owner: "
				+(((this.owner)!=null)?this.owner.getName():"not registered")
				+", driver: "
				+(((d=this.getDriver())!=null)?d.getName():"not registered")
				+".";
	}
	
	/**
	 * Number of wheels
	 * @return the number of wheels
	 */
	public abstract int numWheels();
	
	/**
	 * The necessary permit kind to drive this vehicle
	 * @return the necessary permit kind
	 */
	public abstract PermitKind necessaryPermit();
	
	/**
	 * Calculates the pollution index of the vehicle
	 * @return the pollution index
	 */
	public PollutionIndex getPollutionIndex() {
		return PollutionIndex.getPollutionIndex(this.purchaseYear);
	}
	
	/**
	 * Get the purchase year
	 * @return purchase year of the vehicle
	 */
	public int getPurchaseYear() {
		return purchaseYear;
	}
	/**
	 * Getter for the plate of the vehicle
	 * @return the plate of the vehicle
	 */
	public String getPlate() {
		return this.plate;
	}
	
	/**
	 * Set the driver of a vehicle
	 * @param p possible driver
	 * @return false iff error (if driver is not of age)
	 */
	public boolean setDriver(Person p) {
		if(!(this.hasPermit(p))) {
			return false;
		} else {
			this.driver = p;
			return true;
		}
	}
	
	/**
	 * The driver of the vehicle
	 * @return the driver
	 */
	public Person getDriver() {
		if(this.driver==null && this.owner!=null) {
			Person d=this.owner.getDefaultDriver();
			if (this.hasPermit(d)) {
				return d;
			}
		} 
		return this.driver;/** this may be null, it is ok: 
							* it means that there is no owner or 
							* that the owner does not have a valid 
							* default driver
							*/
	}
	
	/**
	 * Set the owner of the vehicle
	 * @param o owner
	 */
	public void setOwner(Owner o) {
		this.owner = o;
		o.addVehicle(this);
	}
	
	/**
	 * Whether the person has permit to drive this vehicle
	 * @param p person
	 * @return true if the person has a permit for this vehicle
	 */
	public boolean hasPermit(Person p) {
		License license=p.getLicense();
		if (license==null) {
			return false;
		}else if(license.getPermits().contains(this.necessaryPermit())) {
			return true;
		}else {
			return false;
		}
	}	
	
	/* * * * * *   I T V  * * * * * */
	
	/**
	 * New itv passed by the vehicle
	 * @param itv itv that has been passed
	 */
	public void addITV(ITV itv) {
		this.itvs.add(itv);
		itv.getGarage().addVehicle(this);
	}
	
	/**
	 * Get the last ITV passed by the vehicle
	 * @return the last ITV passed by the vehicle. null if there are no itvs passed
	 */
	private ITV getLastITV() {
		if(itvs.isEmpty()) {
			return null;
		}else {
			return itvs.get(itvs.size()-1);
		}
	}
	
	/**
	 * Returns the days until the next itv 
	 * @return an integer that corresponds to the days remaining until 
	 * 	ITV has to be passed, if the number is negative, the ITV is expired 
	 */
	public long daysUntilITV() {
		return this.daysUntilITVAux(itvTimeArray); // uses the static final matrix defined for the class
	}
	
	/**
	 * True iff itv status is ok
	 * @return false iff itv is expired
	 */
	public boolean itvStatus() {
		return (this.daysUntilITV() >= 0);
	}
	
	/**
	 * Returns the days until the next itv using the information in yearsArray 
	 * (this method is made to be reused in subclasses with other year 
	 * configurations for the ITV inspection, like for Truck)
	 * 
	 * @param timeArray is a matrix in the form: {{a1,b1},{a2,b2},{a3,b3}, ..., {an,bn}}.
     * Where a1 < a2 < a3 < ... < an.
	 * Used to calculate the remaining years until the next ITV. 
	 * Meaning: if the age (in years) of the vehicle is lower than a1, 
	 *              it has to pass no inspection,
	 *          if the age of the vehicle is between 
	 * 				a1 and a2, then it has to pass the ITV every b1 months,
	 *          if the age of the vehicle is between 
	 * 				a2 and a3, then it has to pass the ITV every b2 months,
	 *          if the age of the vehicle is between 
	 * 				a3 and a4, then it has to pass the ITV every b3 months, ...
	 *  		..., if the age is higher than an, it has to pass the ITV every bn months
	 *
	 * @return a long that corresponds to the days remaining until 
	 * 	ITV has to be passed, if the number is negative, the ITV is expired 
	 */
	public long daysUntilITVAux(long[][] timeArray) {
		LocalDate now = LocalDate.now();
		int age = now.getYear() - this.getPurchaseYear(); //age of vehicle
		ITV itv = this.getLastITV();
		
		if (itv == null) {
			// has to pass first ITV timeArray[0][0] years after purchase
			// we set by default the purchase date as the first of January
			return (LocalDate.of(purchaseYear, 1, 1).plusYears(timeArray[0][0]).compareTo(now));
		}
		else {
			for (int i = 1; i < timeArray.length; i++) {
				if(age <= timeArray[i][0]) {
					return itv.getDate().plusMonths(timeArray[i-1][1]).compareTo(now);
				}
			}
			//higher than all limits:
			return itv.getDate().plusMonths(timeArray[timeArray.length-1][1]).compareTo(now);
		}
		
	}
}
