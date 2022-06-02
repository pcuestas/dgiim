package app.gui.controller.login;

import java.awt.event.ActionEvent;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.user.LoginPanel;

/**
 * Class SetLoginPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetLoginPanelAction extends SetPanelAction {
	private LoginPanel loginPanel;

	/**
	 * Constructor of SetLoginPanelAction
	 */
	public SetLoginPanelAction() {
		super("loginPanel");
		loginPanel = AppWindow.getInstance().getLoginPanel();
	}
    
    /**
     * updates the login panel action and shows it in the card layout of the frame
     * @param e action event
     */
	@Override public void actionPerformed(ActionEvent e) {
		loginPanel.update();
		super.actionPerformed(e);
	}

}
