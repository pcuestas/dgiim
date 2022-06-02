package app.gui.controller.user.tickets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.MyTicketsPanel;
import app.theater.performances.tickets.Reservation;

/**
 * Class CancelReservationAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CancelReservationAction implements ActionListener {
	private AppWindow frame;
	private MyTicketsPanel panel;

    /**
     * Constructor of CancelReservationAction
     */
	public CancelReservationAction() {
		frame = AppWindow.getInstance();
		panel = frame.getMyTicketsPanel();
	}

    /** 
     * Cancel selected reservation
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Reservation reservation = MainController.getInstance().getCurrentReservation();

        if(reservation == null) return;

        if(JOptionPane.showConfirmDialog(AppWindow.getInstance(), 
           "Are you sure you want to cancel the reservation?", "Cancel?", 
           JOptionPane.YES_NO_OPTION,
           JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
            return ;
        }

		reservation.cancel();

		JOptionPane.showMessageDialog(panel, "Reservation cancelled");
		panel.update(MainController.getInstance().getCurrentClient().getTickets());
		panel.clear();
		panel.setMethodsPanelVisible(false);
	}

}