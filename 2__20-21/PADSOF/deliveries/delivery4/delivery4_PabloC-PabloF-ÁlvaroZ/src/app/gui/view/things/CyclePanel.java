package app.gui.view.things;

/**
 * Class CyclePanel. Panel that displays the info of a given cycle
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CyclePanel extends ThingPanel {

	/**
	 * public constructor
	 */
    public CyclePanel(){
		super("");
		rightCol.remove(selectButton); // not used
		leftCol.remove(labelImage);
	}
}
