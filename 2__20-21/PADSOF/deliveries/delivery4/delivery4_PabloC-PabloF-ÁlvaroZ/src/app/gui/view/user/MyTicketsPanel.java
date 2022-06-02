package app.gui.view.user;

import java.awt.Dimension;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import app.gui.view.payments.PaymentMethodsPanel;
import app.gui.view.tables.ITabulizable;
import app.gui.view.tables.TablePanel;


/**
 * Class MyTicketsPanel. Panel which shows all the tickets of a user (purchased and reserves)
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class MyTicketsPanel extends TablePanel {
	
	private PaymentMethodsPanel methodsPanel = new PaymentMethodsPanel();
    private JButton cancelReservationButton = new JButton("Cancel reservation");
    private JTextArea selectedReservationTextArea = new JTextArea(3, 50);
    
    /**
     * public constructor
     */
    public MyTicketsPanel() {
        super();
        selectButton.setVisible(false);

        selectedReservationTextArea.setEditable(false);
        selectedReservationTextArea.setFont(selectedReservationTextArea.getFont().deriveFont(12f));

        extraPanel.add(selectedReservationTextArea);
        extraPanel.add(methodsPanel);
        extraPanel.add(cancelReservationButton);

        extraPanel.setVisible(false);
    }

    /**
     * updates the name of the selected reservation
     * @param name the reservation name
     */
    public void updateReservationName(String name) {
        selectedReservationTextArea.setText("Selected: \n" + name);
    }

    /**
     * Sets the controller for the panel
     * @param selectAction action performed when select button is perfomed
     * @param cardAction   action perfoemd when confirm reservation button is
     *                     clicked in card pay
     * @param passAction   action performed when confirm reservation button is
     *                     clicked in pass pay
     * @param cancelAction cancel reservation 
     */
    public void setController(ListSelectionListener selectAction, ActionListener cardAction, ActionListener passAction,
            ActionListener cancelAction) {
        this.table.getSelectionModel().addListSelectionListener(selectAction);
        methodsPanel.setController(cardAction, passAction);
        cancelReservationButton.addActionListener(cancelAction);
    }

    /**
     * updates the panel
     * 
     * @param l1 list of tickets
     * @param l2 list of passes
     */
    public void update(java.util.List<? extends ITabulizable> l1, java.util.List<? extends ITabulizable> l2) {
        super.update(l1);
        methodsPanel.update(l2);
    }

    /**
     * sets the panelMethods visibility
     * 
     * @param b boolean value of visibility
     */
    public void setMethodsPanelVisible(boolean b) {
        extraPanel.setVisible(b);
    }

    /**
     * Getter
     * @return the credit card
     */
    public String getCreditCard() {
		return methodsPanel.getCreditCard();
	}
    
    /**
     * Clear the methods panel
     */
    public void clear() {
    	methodsPanel.clear();
    }
    
    
    /** 
     * the selected pass
     * @return ITabulizable
     */
    public ITabulizable getPassSelected() {
		return	methodsPanel.getPassSelected();
	}
}
