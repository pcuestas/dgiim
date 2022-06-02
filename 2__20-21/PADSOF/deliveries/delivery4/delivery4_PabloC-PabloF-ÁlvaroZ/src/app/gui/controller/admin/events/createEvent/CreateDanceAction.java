package app.gui.controller.admin.events.createEvent;

import app.theater.events.*;

/**
 * Class CreateDanceAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreateDanceAction extends CreateEventAction{

    /** 
     * creates a dance
     * @param duration duration of the dance
     * @return Event the new dance
     */
    @Override
    public Event createEvent(int duration) {
        return new Dance(
            createEventPanel.getTitle(), createEventPanel.getDescription(), duration, 
            createEventPanel.getAuthor(), createEventPanel.getDirector(), createEventPanel.getPicture(), 
            createEventPanel.getDancers(), createEventPanel.getConductor(), createEventPanel.getOrchestra());
    }
}