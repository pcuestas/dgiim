package app.gui.controller.user.tickets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.*;
import app.theater.areas.SittingArea;
import app.theater.performances.*;
import app.theater.performances.tickets.Ticket;

/**
 * Class AddTicketsAutomaticAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddTicketsAutomaticAction implements ActionListener {
	AppWindow frame;
	PurchaseTicketsPanel purchaseTicketsPanel;
	
	/**
	 * Constructor of AddTicketsAutomaticAction
	 */
	public AddTicketsAutomaticAction() {
		frame = AppWindow.getInstance();
		purchaseTicketsPanel = frame.getPurchaseTicketsPanel();
	}
    /** 
     * Select tickets with the automatic selection
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Performance performance = MainController.getInstance().getCurrentPerformance();
		SittingArea area;
		try {
			area = (SittingArea) MainController.getInstance().getTicketsTreeSelection().getAreaSelected();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel, 
					"A sitting area has to be selected", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int nTickets=0;

		try {
			nTickets = Integer.parseInt(purchaseTicketsPanel.getAutomaticText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel, 
					"Invalid number", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (nTickets < 1) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel, 
					"Invalid number of tickets", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		AutomaticSelectionType type = purchaseTicketsPanel.getAutomaticSelectionType();
		List<Ticket> tickets = performance.selectAutomatic(area, nTickets, type);
		int size = tickets.size();
		int add=0;
		
		for(int i=0; add<nTickets && i<size; i++) {
			if(purchaseTicketsPanel.addToList(tickets.get(i)))
				add++;
		}
	        
	    if(add<nTickets) {
	    	JOptionPane.showMessageDialog(purchaseTicketsPanel, 
	    			"Could only add "+add+" tickets of "+nTickets,
	    			"Error", JOptionPane.ERROR_MESSAGE);
	    }	 
	}

}
