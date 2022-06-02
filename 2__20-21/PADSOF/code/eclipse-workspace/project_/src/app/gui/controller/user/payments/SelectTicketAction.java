package app.gui.controller.user.payments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.MyTicketsPanel;
import app.theater.performances.tickets.Reservation;
import app.theater.performances.tickets.Ticket;

/**
 * Class SelectTicketAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SelectTicketAction implements ListSelectionListener {
	
	private AppWindow frame;
	private MyTicketsPanel panel;
	private Ticket lastTicket;
	
	/**
	 * Constructor of SelectTicketAction
	 */
	public SelectTicketAction() {
		frame = AppWindow.getInstance();
		panel = frame.getMyTicketsPanel();
	}
	/** 
     * Change the left panel in MyTicketsPanel depending on which tickets has been selected last
     * @param e action event
     */
	@Override
	public void valueChanged(ListSelectionEvent e) {		
		try {
			lastTicket=(Ticket)panel.getSelectedItem();
		}catch (Exception ex) {
			return;
		}
		
		if(lastTicket instanceof Reservation) {
			panel.setMethodsPanelVisible(true);
            panel.updateReservationName(((Reservation)lastTicket).displayReservation());
            MainController.getInstance().setCurrentReservation((Reservation)lastTicket);
		}else {
			panel.setMethodsPanelVisible(false);
		}
	}
	
	/**
	 * Gets the last selected ticket
	 * @return the last selected ticket
	 */
	public Ticket getTicketSelected() {
		return lastTicket;
	}

}
