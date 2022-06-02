package app.gui.controller.login;

import java.awt.event.*;
import javax.swing.*;
import app.theater.Application;
import app.gui.view.AppWindow;
import app.gui.view.user.*;

/**
 * Class RegisterAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class RegisterAction  implements ActionListener{
	private AppWindow frame;
	private LoginPanel panel;
	
	/**
	 * Constructor of RegisterAction
	 */
    public RegisterAction(){
    	this.frame = AppWindow.getInstance();
        this.panel = frame.getLoginPanel();
    }

	
    /** 
     * register a new user with the entered passsword and username
     * @param e action event
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		String name = panel.getUsername();
		String password = panel.getPassword();
		
		if(!Application.getInstance().register(name, password)){
            JOptionPane.showMessageDialog(panel, 
            		"The username " + name + " is already registered.");
        }
	}

}
