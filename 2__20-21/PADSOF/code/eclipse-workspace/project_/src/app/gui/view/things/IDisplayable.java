package app.gui.view.things;

import app.gui.view.tables.ITabulizable;

/**
 * Interface IDisplayable. Objects than implement this interface can be displayed in ThingsPanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public interface IDisplayable extends ITabulizable{
    /**
     * the picture of the displayable
     * @return path to the picture
     */
	public String getPicture();

    /**
     * Small description
     * @return the text
     */
	public String getInfo();
}
