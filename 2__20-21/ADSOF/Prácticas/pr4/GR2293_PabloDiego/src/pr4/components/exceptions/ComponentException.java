package pr4.components.exceptions;

/**
 * Component exception (superclass of the two types of 
 * component exception that can be thrown)
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public abstract class ComponentException extends Exception {
	
	/**
	 * To string method of the exception
	 * @return the message to be shown of the error
	 */
	@Override
	public String toString() {
		return this.message();
	}
	
	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	public abstract String message();
}
