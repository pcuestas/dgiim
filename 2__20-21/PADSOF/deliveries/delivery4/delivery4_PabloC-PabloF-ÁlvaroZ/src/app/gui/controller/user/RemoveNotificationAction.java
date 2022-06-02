package app.gui.controller.user;
import javax.swing.*;
import java.awt.event.*;

import app.gui.view.AppWindow;
import app.gui.view.user.*;
import app.theater.Application;
import app.theater.notifications.Notification;
import app.theater.users.Client;

/**
 * Class RemoveNotificationAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class RemoveNotificationAction implements ActionListener{
    private AppWindow frame;
    private NotificationPanel panel;

    /**
     * Constructor of RemoveNotificationsAction
     */
    public RemoveNotificationAction(){
        frame=AppWindow.getInstance();
        panel = frame.getNotificationPanel();
    }

    /** 
     * Delete notification
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        Notification not;
		try {
			not = (Notification)(panel.getSelectedItem());
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(panel,
					"You have to select a notification", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

        Client c = (Client)(Application.getInstance().getCurrentUser());

        c.removeNotification(not);
        JOptionPane.showMessageDialog(panel, "Notification removed");
        panel.update(c.getNotifications());
    }

}