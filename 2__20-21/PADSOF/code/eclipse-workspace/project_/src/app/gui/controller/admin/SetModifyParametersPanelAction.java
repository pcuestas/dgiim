package app.gui.controller.admin;

import java.awt.event.ActionEvent;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.admin.ModifyParametersPanel;
import app.theater.Application;

/**
 * Class SetModifyParametersPanelAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetModifyParametersPanelAction extends SetPanelAction {
	private ModifyParametersPanel modifyParametersPanel;

	/**
	 * Constructor of SetModifyParametersPanelAction
	 */
	public SetModifyParametersPanelAction() {
		super("modifyParametersPanel");
		this.modifyParametersPanel = AppWindow.getInstance().getModifyParametersPanel();
	}
	
	
    /** 
     * sets the modify parameters panel
     * @param e ActionEvent
     */
    @Override
	public void actionPerformed(ActionEvent e) {
        Application a = Application.getInstance();
		modifyParametersPanel.update("" + a.getTimeAfterReservation(), 
                                    "" + a.getMaxTicketsReservation(), 
                                    "" + a.getMaxTicketsPurchase());
		super.actionPerformed(e);
	}

}
