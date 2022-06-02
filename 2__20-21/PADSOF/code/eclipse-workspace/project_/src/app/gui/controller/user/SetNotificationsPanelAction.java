package app.gui.controller.user;

import java.awt.event.ActionEvent;

import app.gui.controller.*;
import app.gui.view.AppWindow;
import app.gui.view.tables.TablePanel;
import app.gui.view.user.NotificationPanel;
import app.theater.users.Client;

/**
 * Class SetNotificationsPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetNotificationsPanelAction extends SetPanelAction {
	private NotificationPanel notificationPanel;
	private AppWindow frame;
	
	/**
	 * Constructor of SetNotificationsPanelAction
	 */
	public SetNotificationsPanelAction() {
		super("notificationPanel");
		frame = AppWindow.getInstance();
		notificationPanel = frame.getNotificationPanel();
	}
    /** 
     * Set the notifications table
     * @param e action event
     */
	@Override public void actionPerformed(ActionEvent e) {
		Client currClient = MainController.getInstance().getCurrentClient();
		notificationPanel.update(currClient.getNotifications());
		super.actionPerformed(e);
	}
	
}
