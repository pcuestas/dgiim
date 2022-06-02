package app.theater.users;


/**
 * Class RegUser
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public abstract class RegUser extends User {
	private static final long serialVersionUID = 7686864529005226550L;
	
	private String username;
    private String password;
	

    /**
     * Constructor of RegUser
     * @param username registered user's username
     * @param password registered user's password
     */
    public RegUser(String username, String password){
        this.username=username;
        this.password=password;
    }

    /**
     * Checks if the registered user is equal to another
     * @param o registered user to compare with
     * @return true iff it's equal
     */
    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(!(o instanceof RegUser)){
            return false;
        }
        return this.username.equals(((RegUser)o).username);
    }
	
    /**
     * Gets the username
     * @return the username
     */
	public String getName(){
		return this.username;
	}

	/**
	 * Gets the password
	 * @return the password
	 */
	public String getPassword(){
		return this.password;
	}
}
