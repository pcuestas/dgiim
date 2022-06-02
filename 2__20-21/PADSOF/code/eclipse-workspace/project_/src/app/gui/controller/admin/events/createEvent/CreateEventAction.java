package app.gui.controller.admin.events.createEvent;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.admin.CreateEventPanel;
import app.gui.view.things.EventPanel;
import app.theater.Application;
import app.theater.events.Event;

/**
 * Class CreateEventAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public abstract class CreateEventAction extends SetPanelAction {
    protected AppWindow frame;
	protected EventPanel eventPanel;
	protected CreateEventPanel createEventPanel;

    /**
     * Constructor of CreateEventAction
     */
    public CreateEventAction() {
		super("eventPanel");
		frame = AppWindow.getInstance();
		eventPanel = frame.getEventPanel();
		createEventPanel = frame.getCreateEventPanel();
	}
    /** 
     * Creates an event and adds it to the application 
     * @param ev action event
     */ 	
    @Override
    public void actionPerformed(ActionEvent ev){
       	int duration = createEventPanel.getDuration();
       	if(duration == -1) return ;
       	
        Event e = this.createEvent(duration);

        boolean b = Application.getInstance().addEvent(e);
            
        if(!b){ 
            JOptionPane.showMessageDialog(null, "Event could not be added. It was repeated.");
            return;
        }
        
        MainController.getInstance().setCurrentEvent(e);
		eventPanel.update(e, Application.getInstance().isAdminLogged());
		super.actionPerformed(ev);
    }

    /**
     * Creates an event
     * @param duration duration of the event
     * @return the created event
     */
    public abstract Event createEvent(int duration);
}
