package app.gui.controller.admin.performances;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.*;
import app.theater.performances.Performance;

/**
 * Class PostponePerformanceAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PostponePerformanceAction implements ActionListener {
	private PerformancePanel performancePanel;
	
    /**
     * Constructor of PostponePerformanceAction
     */
	public PostponePerformanceAction() {
		this.performancePanel = AppWindow.getInstance().getPerformancePanel();
	}

	
    /** 
     * Postpones a performance when an action is received
     * @param e action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		Performance performance = MainController.getInstance().getCurrentPerformance();
		try{
            LocalDate d = LocalDate.parse(performancePanel.getModifyText());
            if(performance.postpone(d)){
                JOptionPane.showMessageDialog(performancePanel, 
                		"Performance postponed. Notifications will be sent");
            }else{
                JOptionPane.showMessageDialog(performancePanel, 
                		"Invalid date to postpone");
            }
        }catch(DateTimeParseException exc){
			JOptionPane.showMessageDialog(performancePanel, 
					"Incorrect date. The format must be: YYYY-MM-DD");
        }
        AppWindow.getInstance().showPanel("searchPanel");
	}

}
