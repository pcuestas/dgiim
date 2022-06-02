package forms;

import java.util.Map;
import java.util.Scanner;

import forms.fields.Field;

/**
 * AbstractForm class
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class AbstractForm {
	protected Scanner scanner = new Scanner(System.in); // the scanner to receive input from the user
	
	/**
	 * Requests answers of the form to the user
	 * @return the map of answers
	 */
	public abstract Map<String, Comparable<?>> exec();
	
	/**
	 * Request answers to the user
	 * @param <T> the type of the answer
	 * @param question the question
	 * @param field the field
	 * @return the answer
	 */
	protected abstract <T extends Comparable<? super T>> T requestAnswer(String question, Field<T> field);

	/**
	 * Add a new question to the form
	 * @param <T> the type of the answer
	 * @param question the question
	 * @param field the field
	 * @return the answer
	 */
	public abstract <T extends Comparable<? super T>> AbstractForm add(String question, Field<T> field);
}
