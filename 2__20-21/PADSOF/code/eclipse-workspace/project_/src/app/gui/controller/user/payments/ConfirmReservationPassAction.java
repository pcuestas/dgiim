package app.gui.controller.user.payments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.MyTicketsPanel;
import app.theater.passes.Pass;
import app.theater.paymentmethod.PassPay;
import app.theater.performances.tickets.Reservation;
import app.theater.performances.tickets.Ticket;

/**
 * Class ConfirmReservationPassAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class ConfirmReservationPassAction implements ActionListener {
	private AppWindow frame;
	private MyTicketsPanel panel;
	
	/**
	 * Constructor of ConfirmReservationPassAction
	 */
	public ConfirmReservationPassAction() {
		frame = AppWindow.getInstance();
		panel = frame.getMyTicketsPanel();
	}
	
    /** 
     * Confirm a reservation with a pass 
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Reservation res = (Reservation)MainController.getInstance().getSelectTicketAction().
										getTicketSelected();
		
		boolean flag = res.confirm(new PassPay((Pass)panel.getPassSelected()));
		
		if (flag) {
			JOptionPane.showMessageDialog(panel,
					"Reservation confirmed!");
			panel.update(MainController.getInstance().getCurrentClient().getTickets());
			panel.setMethodsPanelVisible(false);
			panel.clear();
		} else {
			JOptionPane.showMessageDialog(panel,
					"Could not confirm the reservation. The pass is not valid", 
					"Error", JOptionPane.ERROR_MESSAGE);
			
		}
	}
}
