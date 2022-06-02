package app.theater.users;

/**
 * Class Admin
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Admin extends RegUser {

	private static final long serialVersionUID = 5332272834187963564L;

	/**
	 * Constructor of admin
	 * @param username admin's username
	 * @param password admin's password
	 */
    public Admin(String username, String password){
        super(username, password);
    }
}
