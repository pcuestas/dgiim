package app.gui.controller.admin.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.EventPanel;
import app.theater.Application;
import app.theater.events.Event;

/**
 * Create performance action class
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreatePerformanceAction implements ActionListener {
	private EventPanel eventPanel;
	
	/**
	 * Constructor
	 */
	public CreatePerformanceAction() {
		eventPanel = AppWindow.getInstance().getEventPanel();
	}

	/**
	 * create a performance 
	 * @param e the pressing of the button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalDate date;
		
		try {
			date = LocalDate.parse(eventPanel.getNewPerformanceDate());
		}catch(Exception exc) {
			JOptionPane.showMessageDialog(eventPanel,
					"Incorrect date format", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Event event = MainController.getInstance().getCurrentEvent();
		
		if(!event.addPerformance(date)) {
			JOptionPane.showMessageDialog(eventPanel,
					"Performance could not be added with that date.\n"
					+"Remember that prices for this event have to be set for every simple area,\n"
					+" and no two performances can be set on the same day.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}else {
			JOptionPane.showMessageDialog(eventPanel,
					"Performance successfuly added.");
			eventPanel.clear();
		}
		
	}

}
