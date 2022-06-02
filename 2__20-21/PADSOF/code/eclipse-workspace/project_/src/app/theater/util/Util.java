package app.theater.util;

/**
 * Class Util
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Util {
    private Util(){};
	/**
	 * Method that checks if a double is or not a percentage
	 * @param p doublle to check
	 * @return true iff it is a percentage
	 */
	public static boolean isPercentage(double p) {
		return ((p<=100) && (p>=0));
	}	
}
