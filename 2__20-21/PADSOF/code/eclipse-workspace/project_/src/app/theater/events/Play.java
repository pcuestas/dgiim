package app.theater.events;

import java.util.*;

/**
 * Class Play
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Play extends Event {
	private static final long serialVersionUID = -50244355180691255L;
	
	private String actors;

	/**
	 * Constrictor of Play
	 * @param title			title of the play
	 * @param description	description of the play
	 * @param durationMin	duration (in minutes) of the play
	 * @param author		author of the play
	 * @param director		director of the play
	 * @param picture 		picture of the play
	 * @param actors		actors of the play
	 */
	public Play(String title, String description, int durationMin, 
			    String author, String director, String picture,
			    String actors) {
		super(title, description, durationMin, author, director, picture);
		this.actors = actors;
	}

	/**
	 * Returns a representative string
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Play - " + super.toString() ;
	}

    /**
     * Method for the table interface
     * @return the type of the event
     */
    @Override
    public String getSmallInfo(){
        return "Play";
    }
}
