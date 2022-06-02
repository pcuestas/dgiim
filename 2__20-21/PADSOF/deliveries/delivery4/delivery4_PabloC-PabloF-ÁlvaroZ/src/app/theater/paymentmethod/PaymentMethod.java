package app.theater.paymentmethod;

import java.io.Serializable;

import es.uam.eps.padsof.tickets.NonExistentFileException;
import es.uam.eps.padsof.tickets.TicketSystem;
import es.uam.eps.padsof.tickets.UnsupportedImageTypeException;
import app.theater.performances.tickets.Ticket;

/**
 * Class PassPay
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public abstract class PaymentMethod implements Serializable{
	private static final long serialVersionUID = -3990815908452716815L;

	/**
	 * Constructor for PaymentMethod (empty)
	 */
    public PaymentMethod(){}

    /**
     * Creates the ticket's pdf after the payment is done
     * @param ticket ticket that has been paid
     * @return true iff the ticket was created correctly
     */
    public boolean pay(Ticket ticket){
        try {
			TicketSystem.createTicket(ticket, "./aux_files/tmp/");
		} catch (NonExistentFileException | UnsupportedImageTypeException e) {
			e.printStackTrace();
			return false;
		}
        return true;
    }
    /**
	 * Restores the pass if the ticket was cancelled
	 * @param ticket ticket cancelled
	 */
    public abstract void payback(Ticket ticket);
}
