package app.theater.areas;

import java.util.*;
import app.theater.areas.locations.Location;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;

/**
 * Class StandingArea
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class StandingArea extends SimpleArea {
	private static final long serialVersionUID = -8852059029514964021L;

	private int capacity;

	/**
	 * Constructor of standing areas
	 * 
	 * @param name     name of the sitting area
	 * @param capacity (of the area)
	 */
	public StandingArea(String name, int capacity) {
		super(name);
		this.capacity = capacity;
	}

	/**
	 * Capacity of the area
	 * @return capacity 
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

    /**
     * Returns the list of tickets generated by this standing area 
     * for the performance performance with the restriction specified
     * @param restriction percentage to be restricted
     * @param performance performance 
     * @return list of the tickets generated
     */
	@Override
	public List<Ticket> getTickets(double restriction, Performance performance) {
		List<Ticket> tickets = new ArrayList<>();
		Location location = new Location(this);
		int restricted = (int) (capacity * (restriction / 100.0));

		for (int i = 0; i < capacity; i++) {
			Ticket t = new Ticket(location, performance);
			if (i < restricted) {
				t.restrict();
			}
			tickets.add(t);
		}
		return tickets;
	}
	
    /**
     * Checks if the area is equal
     * @param o area to be checked
     * @return true iff equal
     */
    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if((!super.equals(o)) || (!(o instanceof StandingArea))){
            return false;
        }
        return (((StandingArea)o).capacity == this.capacity);
    }

	/**
     * Returns a representative string of the area
     * @return the string
     */
	@Override
	public String toString(){
		return "Standing area: "+getName()+". Capacity: "+getCapacity();
	}
}
