package app.theater.areas;

import java.util.*;
import java.io.*;


/**
 * Class Area
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public abstract class Area implements Serializable{
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 1127210549583416035L;
	private String name;

	/**
	 * Constructor of Area
	 * 
	 * @param name name of the area
	 */
	public Area(String name) {
		this.name = name;
	}

	/**
	 * Getter for the area's name
	 * @return area's name
	 */
	public String getName(){
        return this.name;
    }

	/**
	 * Capacity of the area
	 * @return capacity
	 */
	public abstract int getCapacity();

	/**
	 * Gets the simple area(s) of the area 
	 * @return list with the area(s)
	 */
    public abstract List<SimpleArea> getSimpleAreas();

    /**
     * Checks if the area's name is equal
     * @param o area to be checked
     * @return true iff equal
     */
    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(!(o instanceof Area)){
            return false;
        }
        return ((Area)o).name.equals(this.name);
    }

    /**
     * hash code of the area
     * @return hash code of the name
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }
    
    /**
     * Set the annual pass price for this area
     * @param price the price
     */
	public abstract void setAnnualPassPrice(double price);
	
	/**
	 * To string method of an area
	 */
	public String toString() {
		return "[Area: " + this.name 
				+ ". Capacity: " + this.getCapacity() + "]";
	}

	/**
	 * If the area is composite, it removes the area passed as a parameter from it
	 * @param a area to be removed
	 * @return true iff the area was removed
	 */
    public boolean removeArea(Area a){
        return false;
    }
	
}
