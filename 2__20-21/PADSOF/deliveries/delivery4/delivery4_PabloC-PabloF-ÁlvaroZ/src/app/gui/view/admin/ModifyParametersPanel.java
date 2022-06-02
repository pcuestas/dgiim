package app.gui.view.admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Class ModifyParametersPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class ModifyParametersPanel extends JPanel {
	
	private JButton setPurchaseLimitButton = new JButton("Set parameter");
	private JButton setReservationLimitButton = new JButton("Set parameter");
	private JButton setTimeLimitButton = new JButton("Set parameter");

	private JLabel setPurchaseLimitLabel = new JLabel("New purchase limit: ",SwingConstants.CENTER);
    private JTextField purchaseLimitField = new JTextField(20);
	private JLabel setReservationLimitLabel = new JLabel("New reservation limit: ",SwingConstants.CENTER);
    private JTextField reservationLimitField = new JTextField(20);
	private JLabel setTimeLimitLabel = new JLabel("New time limit (days): ",SwingConstants.CENTER);
    private JTextField timeLimitField = new JTextField(20);


	private JLabel timeLabel = new JLabel("Current time limit (days): ",SwingConstants.CENTER);
    private JLabel time = new JLabel();
	private JLabel reservationLabel = new JLabel("Current reservation limit (tickets): ",SwingConstants.CENTER);
    private JLabel reservation = new JLabel();
	private JLabel purchaseLabel = new JLabel("Current purchase limit (tickets): ",SwingConstants.CENTER);
    private JLabel purchase = new JLabel();
    
	/**
	 * Constructor of ModifyParametersPanel
	 */
	public ModifyParametersPanel() {
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		JPanel centerPanel = new JPanel(new GridLayout(3, 2));
		centerPanel.add(purchaseLabel);
		centerPanel.add(purchase);
        centerPanel.add(reservationLabel);
        centerPanel.add(reservation);
        centerPanel.add(timeLabel);
        centerPanel.add(time);
        this.add(centerPanel, BorderLayout.CENTER);

		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Purchase limit", newTab(setPurchaseLimitButton, setPurchaseLimitLabel, purchaseLimitField));
		tabs.add("Reservation limit", newTab(setReservationLimitButton, setReservationLimitLabel, reservationLimitField));
		tabs.add("Time limit after reservation", newTab(setTimeLimitButton, setTimeLimitLabel, timeLimitField));
        this.add(tabs, BorderLayout.SOUTH);
	}

	/**
	 * Creates a new tab for the panel
	 * @param but button in the tab
	 * @param lab label in the tab
	 * @param field text in the tab
	 * @return the new tab
	 */
	private JPanel newTab(JButton but, JLabel lab, JTextField field) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalGlue());
		JPanel panel2 = new JPanel();
		panel.add(panel2);
		panel2.add(lab);
		panel2.add(field);
		panel.add(but);
		but.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(Box.createVerticalGlue());
		return panel;
	}

	/**
	 * Updates the panel
	 * @param time time to update
	 * @param reservation reservation to update
	 * @param purchase purchase to update
	 */
    public void update(String time, String reservation, String purchase){
        this.purchase.setText(purchase);
        this.reservation.setText(reservation);
        this.time.setText(time);
    }

	/**
	 * Sets the controller for the panel
	 * @param setTime action listener to set time
	 * @param setReservations action listener to set reservations
	 * @param setPurchase action listerner to set the purchase
	 */
    public void setController(ActionListener setTime, ActionListener setReservations, ActionListener setPurchase){
        setPurchaseLimitButton.addActionListener(setPurchase);
        setReservationLimitButton.addActionListener(setReservations);
        setTimeLimitButton.addActionListener(setTime);
    }

	/**
	 * Gets the time limit
	 * @return the time limit
	 */
    public String getTimeLimit(){
        return this.timeLimitField.getText();
    }

	/**
	 * Gets the reservations limit
	 * @return the reservations limit
	 */
    public String getReservationsLimit(){
        return this.reservationLimitField.getText();
    }
    
	/**
	 * Gets the purchase limit
	 * @return the purchase limit
	 */
    public String getPurchaseLimit(){
        return this.purchaseLimitField.getText();
    }
}
