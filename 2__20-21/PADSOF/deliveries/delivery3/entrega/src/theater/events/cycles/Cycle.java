package app.theater.events.cycles;

import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import app.theater.events.*;
import app.theater.areas.*;

/**
 * Class Cycle
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Cycle implements Serializable{
    /**
	 * generated serial version UID 
	 */
	private static final long serialVersionUID = 2518134494331644015L;
	
	private List<Event> events;
    private String name;
    private Map<SimpleArea,Double> prices = new HashMap<>();

	/**
	 * Constructor of Cycle
	 * 
	 * @param name       name of the cycle
	 * @param events     events of the cycle
	 * @param reductions dictionary of simple areas and their reductions
	 *                   (percentage)
	 */
	public Cycle(String name, List<Event> events, Map<SimpleArea, Double> reductions) {
		this.name = name;
		this.events = events;

		for (Entry<SimpleArea, Double> entry : reductions.entrySet()) {
			Double price = 0.0;
			for (Event event : this.events) {
				price += event.getPrice(entry.getKey()) * (1 - entry.getValue() / 100.0);
			}
			this.prices.put(entry.getKey(), price);
		}
	}

	/**
	 * Getter of the list of events of the cycle
	 * 
	 * @return List with the events of the cycle
	 */
	public List<Event> getEvents() {
		return Collections.unmodifiableList(events);
	}

	/**
	 * Checks if an event belongs to the cycle
	 * 
	 * @param event event to be checked
	 * @return true if the event belongs to the cycle; false if not
	 */
	public boolean isEventValid(Event event) {
		return events.contains(event);
	}

	/**
	 * Gets the prices for the cycle
	 * @return map with the price of each simple area
	 */
	public Map<SimpleArea, Double> getPrices() {
		return this.prices;
	}
	
	/**
	 * Gets the name of the cycle
	 * @return name of the cycle
	 */
	public String getName() {
		return this.name;
	}

}
