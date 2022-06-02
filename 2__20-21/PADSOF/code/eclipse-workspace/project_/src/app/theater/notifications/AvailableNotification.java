package app.theater.notifications;

import app.theater.performances.Performance;
/**
 * Class Availiable notification
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class AvailableNotification extends Notification{
	
	private static final long serialVersionUID = 8018278199768371935L;
	
	private  Performance performance;
    
	/**
	 * Constructor of Available Notification
	 * @param performance performance that has available seats
	 */
    public AvailableNotification( Performance performance) {
		this.performance = performance;
	}

	/**
	 * Gets the performance associated to the notification
	 * @return the performance
	 */
    @Override
	public Performance getPerformance() {
		return performance;
	}
	
    /**
     * Returns the string to show in the notification
     * @return string to show
     */
	@Override
	public String toString() {
		return "There is an available ticket for performance: " + this.performance.getName()+".";
	}

	/**
	 * Gets the name of the notification
	 * @return "Available tickets"
	 */
	@Override
	public String getTitle(){
		return "Available tickets";
	}

	/**
     * Gets the name of the performance associated to the notification
     * @return the name of the performance
     */
	@Override
	public String getSmallInfo(){
		return performance.getName();
	}

}
