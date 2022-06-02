package app.gui.controller.admin.cycles;

import java.awt.event.ActionEvent;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.admin.CreateCyclePanel;
import app.theater.Application;

/**
 * SetCreateCyclePanelAction class
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetCreateCyclePanelAction extends SetPanelAction {

	private CreateCyclePanel createCyclePanel;
	/** Constructor*/
	public SetCreateCyclePanelAction() {
		super("createCyclePanel");
		createCyclePanel = AppWindow.getInstance().getCreateCyclePanel();
	}
	
	/**
	 * set the panel to create a cycle
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		createCyclePanel.updatePanel();
		createCyclePanel.updateTable(Application.getInstance().getEvents());		
		super.actionPerformed(e);
	}

}
