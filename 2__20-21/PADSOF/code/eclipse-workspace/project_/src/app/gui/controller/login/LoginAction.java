package app.gui.controller.login;

import java.awt.Container;
import java.awt.event.*;

import app.gui.controller.MainController;
import app.gui.controller.areas.PopulateAreaTreeAction;
import app.gui.view.AppWindow;
import app.gui.view.TopPanel;
import app.gui.view.admin.AdminHomePanel;
import app.gui.view.user.*;
import app.theater.Application;
import app.theater.users.*;

import javax.swing.*;

/**
 * Class LoginAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class LoginAction implements ActionListener{
	private AppWindow frame;
	private LoginPanel loginPanel;
	
	/**
	 * Constructor of LoginAction
	 */
    public LoginAction(){
        this.frame = AppWindow.getInstance();
        loginPanel = frame.getLoginPanel();
    }

	
    /** 
     * Tries to log in the user with username and password entered
     * and shows their home panel
     * @param e action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		String name = loginPanel.getUsername();		
		String password = loginPanel.getPassword();
		
		if (name.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(loginPanel,
					"You have to enter a name and a password.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(Application.getInstance().login(name, password)){   
			PopulateAreaTreeAction act = new PopulateAreaTreeAction();
			act.populateTree();
            if(Application.getInstance().isAdminLogged()) { 

        		frame.showPanel("adminHomePanel");
            }else {
        		
        		Client user = (Client)Application.getInstance().getCurrentUser();
        		frame.getClientHomePanel().update(user.getName(), (user).getTickets().size(), (user).getNotifications().size());
        		frame.showPanel("clientHomePanel");
        		MainController.getInstance().setCurrentClient(user);
            }
            TopPanel topPanel = frame.getTopPanel();
            topPanel.updateLogin();
        } else {
            JOptionPane.showMessageDialog(loginPanel, "Login failed", 
            		"Error", JOptionPane.ERROR_MESSAGE);
        }
	}

}
