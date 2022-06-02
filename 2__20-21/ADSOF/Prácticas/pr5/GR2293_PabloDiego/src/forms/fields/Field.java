package forms.fields;

import java.util.*;
import java.util.Map.*;
import java.util.function.*;

import forms.exceptions.*;

/**
 * Field class
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 * @param <T> a comparable class
 */
public class Field<T extends Comparable<? super T>> {
	private Function<String, ? extends T> function;
	private Map<Predicate<T>, String> validations;
	
	/**
	 * Constructor of field
	 * @param func the function to convert a string 
	 * entered by the user to an object of type T
	 */
	public Field(Function<String, ? extends T> func) {
		function = func;
		validations = new LinkedHashMap<>();
	}
	
	/**
	 * Returns the value of the answer
	 * 
	 * @param ans string of the answer, to be transformed by the field
	 * @return the converted value of ans
	 * @throws FormException if incorrect answer 
	 * (InvalidFormatException or InvalidAnswerException)
	 */
	public T getValue(String ans) throws FormException {
		T answer;
		try{
			answer = function.apply(ans);
		}catch(Exception exc) {
			throw new InvalidFormatException();
		}
		this.checkValidations(answer);
		return answer;
	}
	
	/**
	 * Checks all validations of the field
	 * @param answer answer to be checked
	 * @throws InvalidAnswerException if the answer does not pass one validation
	 */
	private void checkValidations(T answer) throws InvalidAnswerException {
		for(Entry<Predicate<T>, String> e: validations.entrySet()) {
			if(!e.getKey().test(answer)) {
				throw new InvalidAnswerException(e.getValue(), answer);
			}
		}
	}

	/**
	 * Add a validation to the field
	 * @param predicate predicate that the answer has to satisfy
	 * @param string message to appear if the validation is not met
	 * @return the field
	 */
	public Field<T> addValidation(Predicate<T> predicate, String string) {
		this.validations.put(predicate, string);
		return this;
	}
}
