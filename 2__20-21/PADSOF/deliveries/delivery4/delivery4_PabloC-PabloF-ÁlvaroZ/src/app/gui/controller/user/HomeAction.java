package app.gui.controller.user;

import javax.swing.*;
import java.awt.event.*;

import app.theater.Application;
import app.theater.users.*;
import app.gui.view.AppWindow;
import app.gui.view.user.ClientHomePanel;

/**
 * Class HomeAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class HomeAction implements ActionListener{
	private AppWindow frame;
	private ClientHomePanel clientHomePanel;
	
    /**
     * Constructor of HomeAction
     */
	public HomeAction() {
	    this.frame = AppWindow.getInstance();
    	clientHomePanel = frame.getClientHomePanel();
	}

    /** 
     * Set the home window of the corresponding registered user
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        RegUser user = Application.getInstance().getCurrentUser();

        if(user == null){
            JOptionPane.showMessageDialog(null, "Login required");
        }else if(!Application.getInstance().isAdminLogged()){
        	clientHomePanel.update(user.getName(), ((Client)user).getTickets().size(), ((Client)user).getNotifications().size());
            frame.showPanel("clientHomePanel");
        }else{
            frame.showPanel("adminHomePanel");
        }
   }
}