package app.theater.searches;

import app.theater.events.Event;

/**
 * Interface ISearchOnly
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public interface ISearchOnly {
	/**
	 * Checks if an event is of a type
	 * @param e event to check
	 * @return true iff the event is of that type 
	 */
	public boolean is(Event e);
}
