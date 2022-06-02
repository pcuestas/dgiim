package app.gui.controller.things;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.tables.PerformancesTablePanel;
import app.gui.view.tables.TablePanel;
import app.gui.view.things.PerformancePanel;
import app.theater.Application;
import app.theater.performances.Performance;

/**
 * Class SetPerformancePanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetPerformancePanelAction extends SetPanelAction {
	private AppWindow frame;
	private PerformancesTablePanel tablePanel;
	private PerformancePanel performancePanel;
	
	/**
	 * Constructor of SetPerformancePanelAction
	 */
	public SetPerformancePanelAction() {
		super("performancePanel");
		frame = AppWindow.getInstance();
		tablePanel = frame.getPerformancesTablePanel();
		performancePanel = frame.getPerformancePanel();
	
    }
	
    /** 
     * Displays the selected performance from the table panel
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Performance performance;
		try {
			performance = (Performance)tablePanel.getSelectedItem();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(tablePanel,
					"You have to select a performance", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
        MainController.getInstance().setCurrentPerformance(performance);

		boolean available;

		if(performance.getAvailableTickets().size()>0){
			available= true;
		}else{
			available = false;
		}
		performancePanel.update(performance, Application.getInstance().isAdminLogged(),available);
		super.actionPerformed(e);
	}
}
