package app.gui.controller.admin.cycles;

import java.awt.event.ActionEvent;
import app.gui.view.AppWindow;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.view.admin.CreateCyclePanel;
import app.theater.events.Event;

/**
 * Class AddEventToCycleAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddEventToCycleAction implements ActionListener {
	
	private CreateCyclePanel panel;
	
	/**
	 * Constructor of AddEventToCycleAction
	 */
	public AddEventToCycleAction() {
		panel = AppWindow.getInstance().getCreateCyclePanel();
	}
		
	
    /** 
     * Adds an event to a cycle when an action is received
     * @param e the event received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		Event event;
		try {
			event = (Event)panel.getSelectItem();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(panel,
					"You have to select an event", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String title = event.getTitle();
		
		if(panel.getDataModel().contains(title)) {
			JOptionPane.showMessageDialog(panel,
					"Event already added", "Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
			
		panel.addToList(event);
	}

}


