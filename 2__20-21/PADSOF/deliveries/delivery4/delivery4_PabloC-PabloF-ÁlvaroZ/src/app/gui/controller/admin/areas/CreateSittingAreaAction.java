package app.gui.controller.admin.areas;

import app.theater.areas.*;

/**
 * Class CreateSittingAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreateSittingAreaAction extends CreateAreaAction{

    /**
     * Creates a new sitting area
     * @return the new sitting area
     */
    @Override
    public Area createArea() {
    	if(super.createArea()==null)
    		return null;
    	
        int rows = createAreaPanel.getRows();
        if (rows<=0){
            return null;
        }

        int columns = createAreaPanel.getColumns();
        if (columns <= 0){
            return null;
        }

        return new SittingArea(
            createAreaPanel.getAreaName(), rows, columns);
    }
}