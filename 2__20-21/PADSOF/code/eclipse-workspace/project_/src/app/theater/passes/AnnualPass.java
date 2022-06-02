package app.theater.passes;

import java.util.*;
import app.theater.areas.*;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;

/**
 * Class AnnualPass
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AnnualPass extends Pass {
	/** generated serialVersionUID*/
	private static final long serialVersionUID = 4438215543090667003L;
	
	private int year;
    private List <Performance> used = new ArrayList<>();

    /**
     * Constructor of AnnualPass
     * @param a			area assigned to the pass
     * @param year		year assigned to the pass
     */
    public AnnualPass(SimpleArea a, int year){
        super(a);
        this.year=year;
    }

	/**
     * Checks if pass can be used to pay a ticket and uses it if it is possible (ads performance to list of used performances)
     * @param t ticket to be purchased
     * @return true iff the pass can be used
     */
    @Override
    public boolean use(Ticket t) {
        if( super.use(t) 
        	&& (t.getPerformance().getDate().getYear() == this.year)
        	&& (!this.used.contains(t.getPerformance()))){
            used.add(t.getPerformance());
            return true;
        }
        return false;
    }
    
    /**
     * Restore (in case of cancellation)
     * @param p performance cancelled
     */
    @Override
    public void restore(Performance p) {
        this.used.remove(p);
    }

    /**
     * Equals method to check whether a pass is equal
     * @param p pass to compare with this 
     * @return true iff this pass is equal to p
     */
    @Override
    public boolean equals(Object p){
        if(this==p){
            return true;
        }
        if(!super.equals(p) || !(p instanceof AnnualPass)){
            return false;
        }

        return (((AnnualPass)p).year==this.year);
    }

    /**
     * Returns a string representative for the pass
     * @return string representing the pass
     */
    @Override
    public String toString(){
        return getTitle()+getSmallInfo();
    }

    /**
     * Gets the name of the pass
     * @return "Annual pass"
     */
	@Override
	public String getTitle() {
		return "Annual pass";
	}

    /**
     * Gets the information of the pass
     * @return the information
     */
	@Override
	public String getSmallInfo() {
		return "[year: "+year+", area: "+super.toString()+"]";
	}
}
