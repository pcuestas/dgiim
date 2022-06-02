package app.theater.notifications;

import app.theater.performances.Performance;

/**
 * Class PostponedNotification
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PostponedNotification extends Notification {
   
	private static final long serialVersionUID = -6227166245091000194L;
	
	private Performance performance ;
    
    /**
     * Constructor of notification
     * @param performance performance associated to the notification
     */
    public PostponedNotification(Performance performance) {
    	this.performance = performance;
    }

    /**
     * Returns the string to show in the notification
     * @return string to show
     */
    @Override
    public String toString() {
        return "Performance was postponed. New schedule: " + this.performance.getName()+".";
    }

    /**
     * Gets the performance postponed
     * @return performance
     */
    public Performance getPerformance(){
    	return performance;
    }
}
