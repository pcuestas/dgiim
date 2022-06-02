package app.gui.controller.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.trees.AreaInfoPanel;
import app.theater.areas.locations.Seat;

/**
 * Class DisableAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class DisableAction implements ActionListener {
	
	private AreaInfoPanel areaInfoPanel;

	/**
	 * Constructor of DisableAction
	 */
	public DisableAction() {
		areaInfoPanel = AppWindow.getInstance().getAreaViewPanel().getAreaInfoPanel();

	}
	
    /** 
     * disable the selected seat
     * @param e action event
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		LocalDate d1;
        LocalDate d2;
		try {
			d1=LocalDate.parse(areaInfoPanel.getBeginText());
			d2=LocalDate.parse(areaInfoPanel.getEndText());
		}catch(DateTimeParseException ex){
			JOptionPane.showMessageDialog(areaInfoPanel, "Invalid date.");
			return;
		}
		
		if(d1.isAfter(d2)) {
			JOptionPane.showMessageDialog(areaInfoPanel, "Second date must be after first date.");
			return;
		}
		
		MainController.getInstance().getSelectSeatAction().getSeat().disable(d1, d2);
		
		areaInfoPanel.update(null,  null, d1+"", d2+"", false);

	}

}
