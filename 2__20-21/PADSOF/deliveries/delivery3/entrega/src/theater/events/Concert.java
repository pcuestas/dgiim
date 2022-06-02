package app.theater.events;

import java.util.*;

/**
 * Class Concert
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Concert extends Event {
	private static final long serialVersionUID = 4362331018902796486L;

	private String program;
	private String orchestra;
	private List<String> soloists;

	/**
	 * Constructor of concert
	 * 
	 * @param title       title of the concert
	 * @param description description of the concert
	 * @param durationMin duration (in minutes) of the concert
	 * @param author      author of the concert
	 * @param director    director of the concert
     * @param picture     picture of the event
	 * @param program     program of the concert
	 * @param orchestra   orchetstra of the concert
	 * @param soloists    solosits of the concert
	 */
	public Concert(String title, String description, int durationMin, 
                    String author, String director, String picture,
			        String program, String orchestra, List<String> soloists) 
    {
		super(title, description, durationMin, author, director, picture);
		this.program = program;
		this.orchestra = orchestra;
		this.soloists = soloists;
	}

	/**
	 * Returns a representative string
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Concert\n" + super.toString() + "\nProgram: " + program + "\nOrchestra: " + orchestra + "\nSoloists: "
				+ soloists;
	}

}
