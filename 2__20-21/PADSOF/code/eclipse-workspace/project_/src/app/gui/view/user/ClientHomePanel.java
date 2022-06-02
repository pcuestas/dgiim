package app.gui.view.user;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import app.gui.view.*;


/**
 * Class ClientHomePanel. Panel that has the info of the user and buttons 
 * for all the user options
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class ClientHomePanel extends JPanel {

	private JButton ticketsButton = new JButton("My tickets");
	private JButton notificationsButton = new JButton("My notifications");
	private JButton cyclePassButton = new JButton("Purchase cycle pass");
	private JButton annualPassButton = new JButton("Purchase annual pass");

	private JLabel user = new JLabel("");
	private JLabel infoTickets = new JLabel("");
	private JLabel infoNotifications = new JLabel("");
	
    
    private static final String notificationsText = "Notifications: ";
    private static final String ticketsText = "Tickets bought/reserved: "; 

	/**
	 * public constructor
	 */
	public ClientHomePanel() {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		placeComponents();
	}
	
	/**
	 * place components of the panel
	 */
	private void placeComponents() {
        JPanel buttonsBox = new JPanel();
        
		insertButtons(buttonsBox);
		
		Util.setFont(user);
		Util.setFont(infoTickets);
		Util.setFont(infoNotifications);
		
		buttonsBox.setAlignmentX(CENTER_ALIGNMENT);
		user.setAlignmentX(CENTER_ALIGNMENT);
		infoTickets.setAlignmentX(CENTER_ALIGNMENT);
		infoNotifications.setAlignmentX(CENTER_ALIGNMENT);
		
		
		this.add(Box.createVerticalStrut(90));
		this.add(user);
		this.add(infoTickets);
		this.add(infoNotifications);
		this.add(Box.createVerticalStrut(20));
		this.add(buttonsBox);
	}

	
    /** 
	 * place all the buttons for the user 
     * @param buttonsBox panel containing the buttons
     */
    private void insertButtons(JPanel buttonsBox) {
        buttonsBox.setLayout(new GridLayout(4, 1));
		buttonsBox.add(ticketsButton);
		buttonsBox.add(notificationsButton);
		buttonsBox.add(cyclePassButton);
		buttonsBox.add(annualPassButton);
	}

    
    /** 
	* updates the JLabels with the user info
     * @param userName name of the user
     * @param nTickets number of tickets
     * @param nNotifications number of notifications
     */
    public void update(String userName, int nTickets, int nNotifications){
		user.setText("Username: " + userName);
        infoTickets.setText(notificationsText + nNotifications);
        infoNotifications.setText(ticketsText + nTickets);
    }

	
    /** 
     * @param ticketsAction action performed when MyTickets button is clicked
     * @param notificationsAction action performed when MyNotifications button is clicked
     * @param cyclePassPurchaseAction action performed when buy CyclePass button is clicked
     * @param annualPassPurchaseAction action performed when buyAnnualPass button is clicked
     */
    public void setController(ActionListener ticketsAction, ActionListener notificationsAction, 
			ActionListener cyclePassPurchaseAction, ActionListener annualPassPurchaseAction) 
	{
		ticketsButton.addActionListener(ticketsAction);
		notificationsButton.addActionListener(notificationsAction);
		annualPassButton.addActionListener(annualPassPurchaseAction);
		cyclePassButton.addActionListener(cyclePassPurchaseAction);
	}
}

