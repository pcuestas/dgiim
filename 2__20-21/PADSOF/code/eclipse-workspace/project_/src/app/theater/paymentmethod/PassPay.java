package app.theater.paymentmethod;

import app.theater.passes.Pass;
import app.theater.performances.tickets.Ticket;

/**
 * Class PassPay
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PassPay extends PaymentMethod {
	private static final long serialVersionUID = -7194690183691696851L;
	
	private Pass pass;
	
	/**
	 * Constructor of PassPay
	 * @param pass 	pass that is going to be used
	 */
	public PassPay(Pass pass) {
		this.pass = pass;
	}

	/**
	 * Pays for a ticket with a pass
	 * @param ticket ticket to be paid
	 * @return true iff the ticket was correctly paid
	 */
	@Override
    public boolean pay(Ticket ticket){
        if( this.pass.use(ticket)){ //Check if the pass is valid
            return super.pay(ticket); //Generate PDF
        }
        return false;
    }

	/**
	 * Restores the pass if the ticket was cancelled
	 * @param ticket ticket cancelled
	 */
    @Override
    public void payback(Ticket ticket) {
        this.pass.restore(ticket.getPerformance());
    }
}
