package app.gui.controller.things;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.tables.PerformancesTablePanel;
import app.theater.Application;
import app.theater.events.Event;
import app.theater.performances.Performance;
import app.theater.util.Interval;

/**
 * Class FilterPerformancesAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class FilterPerformancesAction implements ActionListener {
	
	AppWindow frame;
	PerformancesTablePanel panel;
	
	/**
	 * constructor
	 */
	public FilterPerformancesAction() {
		frame = AppWindow.getInstance();
		panel = frame.getPerformancesTablePanel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		LocalDate d1,d2;
		
		try {
			d1 = LocalDate.parse(panel.getBeginText());
			d2 = LocalDate.parse(panel.getEndText());
		}catch(DateTimeParseException e) {
			JOptionPane.showMessageDialog(panel,
					"Invalid format of dates", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(d1.isAfter(d2)) {
			JOptionPane.showMessageDialog(panel,
					"Second date must be after first date", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(d1.isBefore(Application.getInstance().getCurrentDate())) {
			JOptionPane.showMessageDialog(panel,
					"Date cannot belong to the past. Todays's date: "+Application.getInstance().getCurrentDate(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		List<Performance> list = MainController.getInstance().getResultPerformances();
		Interval inter = new Interval(d1,d2);

		list.removeIf(x -> !inter.belongsToInterval(x.getDate()));
		panel.update(list);
		panel.clear();
	}
}
