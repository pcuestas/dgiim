package app.gui.controller.admin.areas;

import app.theater.Application;
import app.theater.areas.*;

/**
 * Class CreateCompositeAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class CreateCompositeAreaAction extends CreateAreaAction{

    /**
     * Creates a new composite area
     * @return the new composite area
     */
    @Override
    public Area createArea() {
    	
    	if(super.createArea()==null)
    		return null;
    	
        return new CompositeArea(
            createAreaPanel.getAreaName());
    }
}