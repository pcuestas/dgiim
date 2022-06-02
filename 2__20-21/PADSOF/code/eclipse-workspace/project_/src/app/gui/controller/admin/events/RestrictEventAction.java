package app.gui.controller.admin.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.EventPanel;
import app.theater.events.Event;

/**
 * Class RestrictEventAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class RestrictEventAction implements ActionListener{
	private EventPanel eventPanel;
	
	/**
	 * Constructor of RestrictEventAction
     */
	public RestrictEventAction() {
		this.eventPanel = AppWindow.getInstance().getEventPanel();
	}
	
	
    /** 
	 * Add a restriction to an event when an action is received
     * @param act action received
     */
    @Override
	public void actionPerformed(ActionEvent act){
		try{
			Event event = MainController.getInstance().getCurrentEvent();
            Double d = Double.parseDouble(eventPanel.getModifyText());
            if(event.setRestriction(d)){
    			JOptionPane.showMessageDialog(eventPanel, "Success adding new restrictions ");
    		}else{
    			JOptionPane.showMessageDialog(eventPanel, "Failed to add restriction \n(Remember that an event cannot be restricted when there are future performances.)", "Error", JOptionPane.ERROR_MESSAGE);
    		}
        }catch(NumberFormatException exc){
			JOptionPane.showMessageDialog(eventPanel, 
					"Incorrect number for restriction, it has to be a double.");
        }		
	} 

}
