package app.gui.controller.admin.areas;

import app.theater.areas.*;

/**
 * Class CreateStandingAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreateStandingAreaAction extends CreateAreaAction{

    
    /** 
     * Creates an standing area
     * @return new standing area created
     */
    @Override
    public Area createArea() {
    	if(super.createArea()==null)
    		return null;
    	
        int capacity = createAreaPanel.getCapacity();
        if (capacity<=0){
            return null;
        }

        return new StandingArea(
            createAreaPanel.getAreaName(), capacity);
    }
}