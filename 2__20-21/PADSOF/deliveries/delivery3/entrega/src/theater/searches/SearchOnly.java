package app.theater.searches;

import app.theater.events.*;

/**
 * Ennumeration SearchOnly
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public enum SearchOnly implements ISearchOnly{
    /**to search only concerts */
	CONCERT{
		public boolean is(Event event) {
			return (event instanceof Concert);
		}
	},
    /**to search only plays */
	PLAY{
		public boolean is(Event event) {
			return (event instanceof Play);
		}
	},
    /**to search only dances */
	DANCE{
		public boolean is(Event event) {
			return (event instanceof Dance);
		}
	},
    /**to search any type of event */
	ALL{
		public boolean is(Event event) {
			return true;
		}
	};
	
}
