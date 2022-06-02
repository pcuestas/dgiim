package app.theater.users;

import java.io.*;

/**
 * Class User
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class User  implements Serializable{
	/**
	 * generated serial version ID
	 */
	private static final long serialVersionUID = -6211493261844806121L;

	/**
	 * Checks if the user is equal to another
	 * @param u user to compare with
	 * @return true iff it's equal
	 */
	@Override
	public boolean equals(Object u) {
        if(this==u){
            return true;
        }
		return super.equals(u);
	} 

}
