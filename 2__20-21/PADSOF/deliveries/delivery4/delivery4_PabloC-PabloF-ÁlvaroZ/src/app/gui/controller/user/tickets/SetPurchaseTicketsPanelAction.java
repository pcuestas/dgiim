package app.gui.controller.user.tickets;

import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.users.Client;
import app.theater.users.RegUser;
import app.gui.view.user.*;

/**
 * Class SetPurchaseTicketsPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetPurchaseTicketsPanelAction extends SetPanelAction {
	/**
	 * Constructor of SetPurchaseTicketsPanelAction
	 */
	public SetPurchaseTicketsPanelAction() {
		super("purchaseTicketsPanel");
	}
	/** 
     * Set the panel to purchase-reserve tickets
     * @param e action event
     */
	@Override public void actionPerformed(ActionEvent e) {
		PurchaseTicketsPanel purchaseTicketsPanel = AppWindow.getInstance().getPurchaseTicketsPanel();
		RegUser user = Application.getInstance().getCurrentUser();
		if (user == null || Application.getInstance().isAdminLogged()) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel, 
					"You have to log in as client to purchase or reserve tickets", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Client c = (Client) user;
		purchaseTicketsPanel.update(c.getPasses());        
		
		super.actionPerformed(e);
	}
}
