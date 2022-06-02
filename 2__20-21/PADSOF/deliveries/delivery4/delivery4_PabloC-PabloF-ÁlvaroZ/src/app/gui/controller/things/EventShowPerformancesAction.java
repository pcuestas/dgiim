package app.gui.controller.things;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import app.theater.events.*;
import app.theater.performances.Performance;
import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.tables.*;

/**
 * Class EventShowPerformancesaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class EventShowPerformancesAction implements ActionListener {
	private AppWindow frame;
    private PerformancesTablePanel tablePanel;
	private Event event;

	/**
	 * Constructor of EventShowPerformancesAction
	 */
    public EventShowPerformancesAction(){
		frame = AppWindow.getInstance();
        tablePanel = frame.getPerformancesTablePanel();
    }

	
    /** 
     * Shows the performances of the selected event
     * @param e action event
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		event = MainController.getInstance().getCurrentEvent();
        List<Performance> performances = (event).getPerformances(false);
        MainController.getInstance().setResultPerformances(performances);
		tablePanel.update(performances);
        tablePanel.setController(MainController.getInstance().getSetPerformancePanelAction());
		frame.showPanel("performancesTablePanel");
	}
}
