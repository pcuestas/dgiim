package forms.exceptions;

/**
 * FormException thrown when the answer is not considered 
 * valid by the validations of the field
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es) 
 *
 */
public class InvalidAnswerException extends FormException {
	/** message related to the reason of the invalidation*/
	private String message;
	/** invalid answer provided by the user*/
	private Object answer;
	
	/**
	 * Constructor of the exception
	 * @param message message related to the reason of the invalidation
	 * @param answer invalid answer provided by the user
	 */
	public InvalidAnswerException(String message, Object answer) {
		this.message = message;
		this.answer = answer;
	}
	
	/**
	 * toString method of the exception
	 * @return the message of the exception
	 */
	@Override public String toString() {
		return "Invalid value: " + answer + 
				"\n  " + message;
	}
}
