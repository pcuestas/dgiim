package app.gui.controller.user;

import java.awt.event.ActionEvent;

import app.gui.controller.*;
import app.gui.view.AppWindow;
import app.gui.view.tables.TablePanel;
import app.theater.users.Client;

/**
 * Class SetTicketsPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetTicketsPanelAction extends SetPanelAction {
	private TablePanel tablePanel;
	private AppWindow frame;
	
	/**
	 * Constructor of SetTicketsPanelAction
	 */
	public SetTicketsPanelAction() {
		super("myTicketsPanel");
		frame = AppWindow.getInstance();
		tablePanel = frame.getTablePanel();
	}
    /** 
     * Set the tickets table panel
     * @param e action event
     */
	@Override public void actionPerformed(ActionEvent e) {
		Client currClient = MainController.getInstance().getCurrentClient();
		frame.getMyTicketsPanel().update(currClient.getTickets(), currClient.getPasses());
		super.actionPerformed(e);
	}
	
}