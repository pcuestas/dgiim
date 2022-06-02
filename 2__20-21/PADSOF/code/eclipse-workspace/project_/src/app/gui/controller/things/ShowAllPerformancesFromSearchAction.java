package app.gui.controller.things;

import java.awt.event.*;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.tables.PerformancesTablePanel;

/**
 * Class ShowAllPerformancesFromSearchAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class ShowAllPerformancesFromSearchAction implements ActionListener{
    
    private AppWindow frame;
    private PerformancesTablePanel panel;
    /** constructor */
    public ShowAllPerformancesFromSearchAction(){
		frame = AppWindow.getInstance();
        panel = frame.getPerformancesTablePanel();
    }

    /**
     * update the table of performances to show all
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e){
        panel.update(MainController.getInstance().getResultPerformances());
    }

}
