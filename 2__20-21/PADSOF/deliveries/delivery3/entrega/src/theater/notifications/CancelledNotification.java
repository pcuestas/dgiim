package app.theater.notifications;


/**
 * Class CancelledNotification
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CancelledNotification extends Notification {
	
	private static final long serialVersionUID = -6796888770575424568L;
	
	private String performanceName;
	
	/**
	 * Constructor of Cancelled notification
	 * @param getName performance name that corresponded to notification
	 */
    public CancelledNotification(String getName) {
		this.performanceName = getName;
	}

    /**
     * Returns the string to show in the notification
     * @return string to show
     */
	@Override
	public String toString(){
		return "Performance: "+performanceName+" was cancelled.";
	}
    
}
