package app.theater.events.cycles;

import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import app.theater.events.*;
import app.gui.view.things.IDisplayable;
import app.theater.areas.*;

/**
 * Class Cycle
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Cycle implements Serializable, IDisplayable{
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
	
	/**
     * Returns a representative string of the cycle
     * @return the string
     */
	@Override
	public String toString() {
		return name+" "+events+" "+prices;
	}
	
	/*IDisplayable:*/

	/**
     * Gets the title of the cycle
     * @return the title
     */
	@Override
	public String getTitle() {
		return name;
	}

	/**
     * Gets the information of the cycle
     * @return the information
     */
	@Override
	public String getInfo() {
		String s = "<html>" + "Cycle name: " + this.getTitle() + "<br>"
					+"Events in this cycle:";
		for(Event ev:events)
			s += "<br> - " + ev.getTitle();
		
		s += "<br>Prices in each area:";
		for(Map.Entry<SimpleArea, Double> en: prices.entrySet())
			s += "<br>" + en.getKey().getName() + ": " + String.format(Locale.ROOT, "%.2f",en.getValue());
		
		return s + "</html>";
	}

	/**
     * Gets the picture of the cycle
     * @return the picture
     */
	@Override
	public String getPicture() {
		return this.events.get(0).getPicture();
	}

	/**
     * Gets the reduced information of the cycle
     * @return the reduced information
     */
	@Override
	public String getSmallInfo() {
		return events.stream().<String>map(e -> e.getTitle()).reduce("", (s,t)-> s + "-" + t);
	}

}
