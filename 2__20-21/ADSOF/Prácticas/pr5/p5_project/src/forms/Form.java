package forms;

import java.util.*;

import forms.fields.Field;
import forms.exceptions.*;

/**
 * Form class
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es) 
 */
public class Form extends AbstractForm {
	/** each question of the form */
	private Map<String, Field<?>> entries = new LinkedHashMap<>();

	/**
	 * Adds a question to the form
	 * @param <T> a comparable class
	 * @param question the question
	 * @param field the field
	 * @return the form
	 */
	@Override
	public <T extends Comparable<? super T>> AbstractForm add(String question, Field<T> field) {
		entries.put(question, field);
		return this;
	}

	/**
	 * Requests the user the answers to the questions of the form
	 * @return the map of answers
	 */
	@Override
	public Map<String, Comparable<?>> exec() {
		LinkedHashMap<String, Comparable<?>> answers = new LinkedHashMap<>();
		for (String s : entries.keySet()) {
			answers.put(s, requestAnswer(s, entries.get(s)));
		}
		return answers;
	}

	/**
	 * Requests an answer to the user until the answer is correct 
	 * (this is determined by the field)
	 * @param <T> the class of the answer
	 * @param question question
	 * @param field the field
	 * @return the answer
	 */
	@Override
	protected <T extends Comparable<? super T>> T requestAnswer(String question, Field<T> field) 
	{
		while(true) {
			System.out.printf("%s > ", question);
			String ans = scanner.nextLine(); // read input
			try {
				return field.getValue(ans);
			}catch(FormException e) {// in case the answer is invalid
				System.out.println(e);
			}
		}
	}

}
