package app.gui.controller.search;

import java.awt.event.ActionEvent;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.user.SearchPanel;

/**
 * Class SetSearchPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetSearchPanelAction extends SetPanelAction {
	SearchPanel searchPanel;
	
	/**
	 * Constructor of SetSearchPanelAction
	 */
	public SetSearchPanelAction() {
		super("searchPanel");
		searchPanel = AppWindow.getInstance().getSearchPanel();
	}
	
    /**
     * Update the search panel and display it 
     * @param e action event
     */
	@Override 
    public void actionPerformed(ActionEvent e) {
		searchPanel.update();
		super.actionPerformed(e);
	}
}
