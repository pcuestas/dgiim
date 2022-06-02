package app.theater.notifications;

import java.io.Serializable;

/**
 * Abstract Class Notification
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public abstract class Notification implements Serializable {
	private static final long serialVersionUID = -6943629698896685548L;
	
	private boolean seen = false;
    
    /**
     * To string method
     */
    public abstract String toString();

    
}