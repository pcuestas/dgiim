package app.theater.passes;

import java.io.*;

import app.gui.view.tables.ITabulizable;
import app.theater.areas.*;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;

/**
 * Abstract Class Pass
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public abstract class Pass implements Serializable,ITabulizable{
    /** generated serialVersionUID*/
	private static final long serialVersionUID = 7962695985929389471L;
	
	private SimpleArea area;

    /**
     * Constructor of pass
     * @param a area of the pass
     */
    public Pass(SimpleArea a){
        this.area=a;
    }

    /**
     * Makes the changes needed in the pass after it has
     * been used to pay for this ticket
     * @param t ticket to pay for
     * @return True iff the pass can be used for this pass
     */
    public boolean use(Ticket t){
        return (this.area == t.getArea());
    }

    /**
     * Restores the event in the pass - for the payback
     * @param performance performance to be restored
     */
    public abstract void restore(Performance performance);

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
    	if(p instanceof Pass)
    		return (((Pass)p).area.equals(this.area));
    	return false;
    }

    /**
     * Returns a string representative for the pass
     * @return string representing the pass
     */
    @Override
    public String toString(){
        return area.getName();
    }
}
