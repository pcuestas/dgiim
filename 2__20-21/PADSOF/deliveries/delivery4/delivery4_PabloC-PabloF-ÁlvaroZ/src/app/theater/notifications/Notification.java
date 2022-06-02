package app.theater.notifications;

import java.io.Serializable;

import app.gui.view.tables.ITabulizable;
import app.theater.performances.*;

/**
 * Abstract Class Notification
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public abstract class Notification implements Serializable, ITabulizable {
	private static final long serialVersionUID = -6943629698896685548L;
	
	private boolean seen = false;
    
    /**
     * To string method
     */
	@Override
    public abstract String toString();

    /**
     * Gets the performance of the notification
     * @return by default null
     */
    public Performance getPerformance(){
        return null;
    }
    
}