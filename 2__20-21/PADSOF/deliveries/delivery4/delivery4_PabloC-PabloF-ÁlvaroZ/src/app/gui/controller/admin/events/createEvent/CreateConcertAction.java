package app.gui.controller.admin.events.createEvent;

import app.theater.events.Concert;
import app.theater.events.Event;

/**
 * Class CreateConcertAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreateConcertAction extends CreateEventAction{

    
    /** 
     * creates a concert
     * @param duration duration of the concert
     * @return Event the new concert
     */
    @Override
    public Event createEvent(int duration) {
        return  new Concert(
            createEventPanel.getTitle(), createEventPanel.getDescription(), duration, 
            createEventPanel.getAuthor(), createEventPanel.getDirector(), createEventPanel.getPicture(), 
            createEventPanel.getProgram(), createEventPanel.getOrchestra(), createEventPanel.getSoloists());
    }
}