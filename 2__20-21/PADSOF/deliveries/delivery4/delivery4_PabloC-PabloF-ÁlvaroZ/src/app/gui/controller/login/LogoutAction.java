package app.gui.controller.login;

import app.gui.view.AppWindow;
import app.gui.view.TopPanel;
import app.theater.Application;
import java.awt.event.*;

import javax.swing.JOptionPane;

/**
 * Class LogoutAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class LogoutAction implements ActionListener{
    private TopPanel topPanel;
    private AppWindow frame;
    
    /**
     * Constructor of LogoutActio
     */
    public LogoutAction() {
    	frame = AppWindow.getInstance();
    	topPanel = frame.getTopPanel();
    }

    
    /** 
     * logs out the current user
     * @param e button 'logout' has been pressed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(!Application.getInstance().logout()) {
        	JOptionPane.showMessageDialog(topPanel,
					"You can not log out. First you have to log in.", 
					"Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            frame.getTopPanel().updateLogout();
            frame.getLoginPanel().update();
            frame.showPanel("loginPanel");
        }
    }
}
