package forms.exceptions;

/**
 * FormException thrown when the format of the answer is not correct
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es) 
 *
 */
public class InvalidFormatException extends FormException {
	/**
	 * toString method of the exception
	 * @return the message of the exception
	 */
	@Override public String toString() {
		return "Invalid format";
	}
}
