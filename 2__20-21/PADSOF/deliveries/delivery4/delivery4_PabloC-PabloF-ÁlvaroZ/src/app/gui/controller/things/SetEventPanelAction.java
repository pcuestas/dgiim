package app.gui.controller.things;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.tables.TablePanel;
import app.gui.view.things.EventPanel;
import app.theater.Application;
import app.theater.events.Event;

/**
 * Class SetEventPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetEventPanelAction extends SetPanelAction {
	private AppWindow frame;
	private TablePanel tablePanel;
	private EventPanel eventPanel;
	private Event event;
	
	/**
	 * Constructor of SetEventPanelAction
	 */
	public SetEventPanelAction() {
		super("eventPanel");
		frame = AppWindow.getInstance();
		tablePanel = frame.getTablePanel();
		eventPanel = frame.getEventPanel();
	}
	
	
    /** 
     * Set the selected event panel from the table panel
     * @param e action performed
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		try {
			event = (Event)tablePanel.getSelectedItem();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(tablePanel,
					"You have to select an event", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
        eventPanel.update(event, Application.getInstance().isAdminLogged());
        MainController.getInstance().setCurrentEvent(event);
		super.actionPerformed(e);
	}
    
    /** 
     * The last selected event
     * @return Event the last selected event
     */
    public Event getEvent(){
        return event;
		
    }
	
}
