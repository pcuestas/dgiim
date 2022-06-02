package app.gui.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import app.gui.controller.user.HomeAction;

/**
 * Class TopPanel. This panel is always displayed at the top of the window and has threa buttons with main 
 * operations
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class TopPanel extends JPanel {

	JButton regButton  = new JButton("Register/Login");
	JButton searchButton  = new JButton("Search");
	JButton homeButton = new JButton("Home");
    
    private ActionListener logoutAction;
    private ActionListener regLoginSetPanel;

    /**
    * Public constructor
     */
    public TopPanel() {
        this.setLayout(new FlowLayout());
		
		this.add(searchButton);
		this.add(regButton);
		this.add(homeButton);
		this.setBackground(Color.gray);
	}
    

    /**
    * @param regLoginSetPanel action when login button is pressed
    * @param searchSetPanel action when search button is pressed
    * @param homeAction action when home button is pressed
    * @param logoutAction action when logout button is pressed
     */
    public void setController(ActionListener regLoginSetPanel, 
    		ActionListener searchSetPanel, HomeAction homeAction,
    		ActionListener logoutAction) 
    {
    	regButton.addActionListener(regLoginSetPanel);        
        searchButton.addActionListener(searchSetPanel);
		homeButton.addActionListener(homeAction);
		this.logoutAction = logoutAction;
        this.regLoginSetPanel = regLoginSetPanel;
    }

    /**
    * updates the panel once login has been done
     */
    public void updateLogin(){
        regButton.setText("Logout");
        regButton.removeActionListener(regLoginSetPanel);
        regButton.addActionListener(logoutAction);
    }
    

    /**
    * updates the panel once logOut has been done
     */
    public void updateLogout(){
        regButton.setText("Register/Login");
        regButton.removeActionListener(logoutAction);
        regButton.addActionListener(regLoginSetPanel);
    }
}
