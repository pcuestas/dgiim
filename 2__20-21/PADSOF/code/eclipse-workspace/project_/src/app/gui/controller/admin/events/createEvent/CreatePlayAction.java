package app.gui.controller.admin.events.createEvent;

import app.theater.events.*;

/**
 * Class CreatePlayAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreatePlayAction extends CreateEventAction{

    /** 
     * creates a play
     * @param duration duration of the play
     * @return Event the new play
     */
    @Override
	public Event createEvent(int duration){
		return new Play(
            createEventPanel.getTitle(), createEventPanel.getDescription(), duration, 
            createEventPanel.getAuthor(), createEventPanel.getDirector(), createEventPanel.getPicture(), 
            createEventPanel.getActors());
	}
}
