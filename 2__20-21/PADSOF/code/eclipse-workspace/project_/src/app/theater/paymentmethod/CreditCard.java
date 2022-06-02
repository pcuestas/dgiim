package app.theater.paymentmethod;

import es.uam.eps.padsof.telecard.*;
import app.theater.performances.tickets.Ticket;


/**
 * Class CreditCard
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreditCard extends PaymentMethod {
	private static final long serialVersionUID = -8530210312873347033L;
	
	private String cardNumber;
	
	/**
	 * Constructor of CreditCard
	 * @param cardNumber number of the credit card
	 */
	public CreditCard(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	/**
	 * Pays for a ticket with the credit card without printing a trace
	 * @param ticket ticket to pay
	 * @return true iff the ticket was paid correctly
	 */
    @Override
    public boolean pay(Ticket ticket){        
        return this.pay(ticket, false);  // by default false      
    }

    /**
     * Pays for a ticket with the credit card printing or not (depending on the parameter trace) a trace
     * @param ticket ticket to pay
     * @param trace if true prints a trace
     * @return true iff the ticket was paid correctly
     */
	public boolean pay(Ticket ticket, boolean trace){
        if( this.charge(ticket+"", -(ticket.getPrice()), trace)){
            return super.pay(ticket); // where the pdf is generated
        }else{
            return false;
        }
    }
    
    /**
     * Charges and handles the Exception
     * @param subject subject of the payment
     * @param amount amount to be charged
     * @param trace if true prints a trace
     * @return true iff the charge is successfully done
     */
    public boolean charge(String subject, double amount, boolean trace){
        if (!TeleChargeAndPaySystem.isValidCardNumber(this.cardNumber)){
            return false;
        }
        
        try {
        	amount=Math.round(amount*100.00)/100.0;
			TeleChargeAndPaySystem.charge(this.cardNumber, subject, amount, trace);
	        return true;
		} catch (OrderRejectedException e) {
			e.printStackTrace();
			return false;
		}
    }

    /**
     * Returns the money to the card if a ticket is cancelled
     * @param ticket ticket that has been cancelled
     */
    @Override
    public void payback(Ticket ticket){
		this.charge(ticket+"", ticket.getPrice(), false);
    }
}
