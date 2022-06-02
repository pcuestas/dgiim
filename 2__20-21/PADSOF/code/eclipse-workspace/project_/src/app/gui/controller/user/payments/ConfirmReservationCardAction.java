package app.gui.controller.user.payments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.MyTicketsPanel;
import app.theater.paymentmethod.CreditCard;
import app.theater.performances.tickets.Reservation;

/**
 * Class ConfirmReservationCardAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class ConfirmReservationCardAction implements ActionListener {

	private AppWindow frame;
	private MyTicketsPanel panel;

	/**
	 * Constructor of ConfirmReservationCardAction
	 */
	public ConfirmReservationCardAction() {
		frame = AppWindow.getInstance();
		panel = frame.getMyTicketsPanel();
	}

	
    /** 
     * Confirm a reservation with the card 
     * @param e action event
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		Reservation res = MainController.getInstance().getCurrentReservation();

		boolean flag = res.confirm(new CreditCard(panel.getCreditCard()));

		if (flag) {
			JOptionPane.showMessageDialog(panel,
					"Reservation confirmed!");
			panel.update(MainController.getInstance().getCurrentClient().getTickets());
			panel.clear();
			panel.setMethodsPanelVisible(false);
		} else {
			JOptionPane.showMessageDialog(panel,
					"Could not confirm the reservation", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}