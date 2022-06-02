package pr4.races.exceptions;

/**
 * Exceptions that can be thrown in the races
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public abstract class RaceException extends Exception {
	
	/**
	 * Information of the exception
	 * @return the string containing the cause of the exception 
	 */
	public String toString() {
		return this.message();
	}
	
	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	public abstract String message();
}
