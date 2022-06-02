package pr4.races.exceptions;

/**
 * Invalid speed exception.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class InvalidSpeedException extends RaceException {

	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	@Override
	public String message() {
		return "Maximum speed must be a non negative value, "
				+ "lower than the length of the race.";
	}

}
