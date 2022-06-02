package forms;

import java.util.*;

import forms.exceptions.FormException;
import forms.fields.Field;

/**
 * The ProtectedForm class, uses the proxy pattern to protect a Form
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es) 
 */
public class ProtectedForm extends AbstractForm {
	private Form form; // the form that is being protected
	private Field<String> passwordField; // the password field
	private boolean locked = true; // whether the form is locked
	
	/**
	 * Constructor of protected form
	 * @param form the form to protect
	 * @param password the password
	 */
	private ProtectedForm(Form form, String password) {
		this.form = form;
		this.passwordField = new Field<>(s -> s).
				addValidation(s -> s.contentEquals(password), "");
	}
	
	/**
	 * Protect a Form with a password
	 * @param form the form to be protected
	 * @param password the password
	 * @return the new protected form
	 */
	public static ProtectedForm protect(Form form, String password) {
		return new ProtectedForm(form, password);
	}
	
	/**
	 * Execute the form, protected by password. 
	 * The password will be requested up to 3 times to the user.
	 * If the password is correct, the protected form will be unlocked
	 * and future calls to this method will not request the password.
	 * @return the map of answers
	 */
	@Override
	public Map<String, Comparable<?>> exec(){
		if(locked) {
			this.requestAnswer("Enter password: ", passwordField);
		}
		if(locked) {// if not unlocked, empty answers
			System.out.println("Form locked");
			return Collections.emptyMap();
		}
		
		return form.exec();
	}
	/**
	 * Request the password to the user. After execution, the attribute
	 * locked will change to true if the password is entered by the user.
	 * Otherwise, it will remain false.
	 * @param s the question
	 * @param field 
	 * @return the correct answer or null if the user does not enter it correctly
	 */
	@Override
	protected <T extends Comparable<? super T>> T requestAnswer(String s, Field<T> field) {
		int attempts = 3;
		while((attempts--) > 0) {
			System.out.printf("%s", s);
			try {
				T pass = field.getValue(scanner.nextLine());
				locked = false;
				return pass;
			} catch (FormException e) {
				System.out.println("Invalid password (" + attempts + " remaining attempts)");
			}
		}
		return null;
	}
	
	/**
	 * Adds a question to the form
	 * @param <T> a comparable class
	 * @param question the question
	 * @param field the field
	 * @return the form
	 */
	@Override
	public <T extends Comparable<? super T>> AbstractForm add(String question, Field<T> field) {
		form.add(question, field);
		return this;
	}
}
