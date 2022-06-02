package app.gui.controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.PerformancePanel;
import app.gui.view.user.NotificationPanel;
import app.theater.Application;
import app.theater.notifications.*;
import app.theater.performances.Performance;

/**
 * Class SetPerformancePanelFromNotificationAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetPerformancePanelFromNotificationAction implements ActionListener {
	
	private AppWindow frame;
	private NotificationPanel panel;
	private PerformancePanel perfPanel;
	
	/**
	 * Constructor of SetPerformancePanelFromNotificationAction
	 */
	public SetPerformancePanelFromNotificationAction() {
		frame = AppWindow.getInstance();
		panel = frame.getNotificationPanel();
		perfPanel = frame.getPerformancePanel();
	}

    /** 
     * Set the performance panel from the notification panel
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
	
		Notification notif;
		try {
			notif = (Notification)panel.getSelectedItem();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(panel,
					"You have to select a performance", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(notif instanceof CancelledNotification) {
			JOptionPane.showMessageDialog(panel,
					"Option not available for cancelled performances", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
        MainController.getInstance().setCurrentPerformance(notif.getPerformance());

		Performance performance = MainController.getInstance().getCurrentPerformance();
		boolean bool = (performance.getAvailableTickets().size()>0);
		perfPanel.update(notif.getPerformance(), Application.getInstance().isAdminLogged(),bool);
		frame.showPanel("performancePanel");
	}

}
