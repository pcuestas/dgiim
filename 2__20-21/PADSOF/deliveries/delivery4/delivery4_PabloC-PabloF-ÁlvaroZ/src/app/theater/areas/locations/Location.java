package app.theater.areas.locations;

import app.theater.areas.*;
import java.time.*;
import java.io.*;

/**
 * Class location
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 */
public class Location implements Serializable{
	/**
	 * generated serial version UID 
	 */
	private static final long serialVersionUID = 5172060242188921952L;
	
	protected SimpleArea area;

	/**
	 * Constructor of Location
	 * @param area area of the location
	 */
	public Location(SimpleArea area) {
		this.area = area;
	}
	/**
	 * Empty constructor
	 */
	public Location() {}
	
	/**
	 * Get area
	 * @return the area of the location
	 */
	public SimpleArea getArea() {
		return area;
	}

    /**
    * Is available 
    * @param d date to check
    * @return true iff it is available
    */
    public boolean isDisabled(LocalDate d) {
        return false;
    }

    /**
     * Representative string
     * @return name of the location's area
     */
	@Override 
	public String toString(){
        return this.area.getName();
    }

}
