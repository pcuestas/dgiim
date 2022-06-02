package app.theater.performances;

/**
 * Ennumeration AutomaticSelectionType
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public enum AutomaticSelectionType {
    /**centered in the theater */
	CENTERED, 
    /**centered in the lower rows */
    CENTEREDLOWER, 
    /**centerd in the upper rows */
    CENTEREDUPPER,
	/**furthest away from the center of mass*/
	FURTHEST;
}
