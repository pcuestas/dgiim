package app.theater.events;

import java.util.List;

/**
 * Class Dance
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Dance extends Event {
	private static final long serialVersionUID = 1781118664076431846L;
	
	private List<String> dancers;
	private String conductor;
	private String orchestra;
	
	/**
	 * Constructor of Dance
	 * @param title			title of the dance
	 * @param description	description of the dance
	 * @param durationMin	duration (in minutes) of the dance
	 * @param author		author of the dance
	 * @param director		director of the dance
	 * @param picture		picture of the dance
	 * @param dancers		dancers of the dance
	 * @param conductor		conductor of the dance
	 * @param orchestra		orchestra of the dance
	 */
	public Dance(String title, String description, int durationMin, 
			     String author, String director, String picture,
			     List<String> dancers, String conductor, String orchestra) 
    {
		super(title, description, durationMin, author, director, picture);
		this.dancers = dancers;
		this.conductor = conductor;
		this.orchestra = orchestra;
	}
	
	/**
	 * Returns a representative string
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Dance\n" + super.toString() + "\nDancers: " + dancers + "\nOrchestra: " + orchestra + "\nConductor: "
				+ conductor;
	}
	
}
