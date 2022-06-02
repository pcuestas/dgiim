package app.gui.controller;

import java.awt.event.*;

import app.gui.view.AppWindow;

/**
 * Class SetPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetPanelAction implements ActionListener {
	private String panelName;
	
	/** 
	 * Constructor of SetPanelAction
	 * @param panelName panel to be shown
	 */
	public SetPanelAction(String panelName) {
		this.panelName = panelName;
	}

	/**
	 * Shows the panel passed in the constructor when an action is received
	 * @param e action received
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AppWindow.getInstance().showPanel(panelName);
	}

}
