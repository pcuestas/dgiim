package pr4.components.exceptions;

/**
 * Invalid component name, used when an invalid name is entered
 * as a component.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class InvalidComponentName extends ComponentException {
	private String name;

	/**
	 * Constructor of the exception
	 * @param name name of the non valid component
	 */
	public InvalidComponentName(String name) {
		this.name = name;
	}

	/**
	 * Message of the exception
	 * @return the message of the exception
	 */
	@Override
	public String message() {
		return "Invalid component name: " + this.name;
	}
	
}
