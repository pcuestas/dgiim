package pr4.races.exceptions;

/**
 * Race exception: RaceSmall, when the length of the race is smaller 
 * than some of the speeds is bigger than the lenght of the race
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class RaceSmallException extends RaceException {

	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	@Override
	public String message() {
		return "The race is too small, the lenght should be higher"
				+ " than every maximum speed";
	}

}
