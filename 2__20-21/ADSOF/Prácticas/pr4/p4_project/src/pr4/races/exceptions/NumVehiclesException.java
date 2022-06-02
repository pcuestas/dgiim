package pr4.races.exceptions;

/**
 * Number of vehicles exception.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class NumVehiclesException extends RaceException {

	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	@Override
	public String message() {
		return "Total number of vehicles not between 2 and "
				+ "10 (both included).";
	}

}
