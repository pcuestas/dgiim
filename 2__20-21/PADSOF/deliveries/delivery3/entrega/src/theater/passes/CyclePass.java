package app.theater.passes;

import java.util.*;
import app.theater.events.*;
import app.theater.events.cycles.Cycle;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;
import app.theater.areas.*;

/**
 * Class CyclePass
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CyclePass extends Pass {
	/** generated serialVersionUID*/
	private static final long serialVersionUID = -344673909140039257L;
	
	private Cycle cycle;
    private List<Event> used = new ArrayList<>();

    /**
     * Constructor for the cycle pass
     * @param a			area of the pass
     * @param cycle		cycle of the pass
     */
    public CyclePass(SimpleArea a, Cycle cycle){
        super(a);
        this.cycle=cycle;
    }

    /**
     * Purchase a ticket with the pass
     * @param t ticket to be purchased
     * @return true iff the pass can be used to purchase this ticket
     */
    @Override
    public boolean use(Ticket t){
        Event event = t.getPerformance().getEvent();
        if(super.use(t) 
             && this.cycle.isEventValid(event) 
             && !this.used.contains(event))
        {
            used.add(event);
            return true;
        }
        
        return false;
    }

    /**
     * Restores the event in the pass - for the payback
     * @param performance performance of the event to be restored
     */
    @Override 
    public void restore(Performance performance) {
        used.remove(performance.getEvent());
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
        if(!super.equals(p) || !(p instanceof CyclePass)){
            return false;
        }

        return (((CyclePass)p).cycle==this.cycle);
    }

    /**
     * Returns a string representative for the pass
     * @return string representing the pass
     */
    @Override
    public String toString(){
        return "Cycle pass [cycle: "+cycle.getName()
                +", area: "+super.toString()+"]";
    }
}
