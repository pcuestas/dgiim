package app.gui.controller.admin.performances;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.*;

/**
 * Class CancelPerformanceAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CancelPerformanceAction implements ActionListener {
	private PerformancePanel performancePanel;
	
	/**
	 * Constructor of CancelPerformanceAction
	 */
	public CancelPerformanceAction() {
		this.performancePanel = AppWindow.getInstance().getPerformancePanel();
	}

	
    /** 
	 * Cancels a performance when an action is received
     * @param e action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		MainController.getInstance().getCurrentPerformance().cancel();
        JOptionPane.showMessageDialog(performancePanel, 
        		"Performance cancelled. Notifications will be sent");
        AppWindow.getInstance().showPanel("searchPanel");
	}

}
