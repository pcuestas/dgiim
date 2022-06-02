package app.gui.view.tables;

/**
 * Public Iinterface ITabulizable. Obejcts of classes that implements this interface
 * can be displayed in tables
 *
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public interface ITabulizable {
	
	/**
	 * Title
	 * @return the title
	 */
	public String getTitle();
	
	/**
	 * Small description or information
	 * @return the string
	 */
	public String getSmallInfo();
}
