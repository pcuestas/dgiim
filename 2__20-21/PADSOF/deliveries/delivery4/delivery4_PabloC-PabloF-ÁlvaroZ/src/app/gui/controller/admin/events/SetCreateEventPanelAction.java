package app.gui.controller.admin.events;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.admin.CreateEventPanel;

import java.awt.event.*;

/**
 * Class SetCreateEventPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetCreateEventPanelAction extends SetPanelAction {
    CreateEventPanel createEventPanel;

    /**
     * Constructor of SetCreateEventPanelAction
     */
    public SetCreateEventPanelAction(){
        super("createEventPanel");
        createEventPanel = AppWindow.getInstance().getCreateEventPanel();
    }

    
    /** 
     * clears the event panel and sets the panel 
     * @param e action event
     */
    @Override
	public void actionPerformed(ActionEvent e) {
        createEventPanel.clear();
		super.actionPerformed(e);
	}

}
