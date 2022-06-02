package app.gui.controller.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.admin.ModifyParametersPanel;
import app.theater.Application;

/**
 * Enumeration SetParameterAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public enum SetParameterAction implements ActionListener {
	TIME_LIMIT{
		@Override
		protected String getValue() {
			return modifyParametersPanel.getTimeLimit();
		}

		@Override
		protected void setParameter(int i) {
			Application.getInstance().setTimeAfterReservation(i);
		}
		
	},
	RESERVATIONS_LIMIT{
		@Override
		protected String getValue() {
			return modifyParametersPanel.getReservationsLimit();
		}

		@Override
		protected void setParameter(int i) {
			Application.getInstance().setMaxTicketsReservation(i);
		}
	}, 
	PURCHASE_LIMIT{
		@Override
		protected String getValue() {
			return modifyParametersPanel.getPurchaseLimit();
		}
		@Override
		protected void setParameter(int i) {
			Application.getInstance().setMaxTicketsPurchase(i);
		}
	};
		
	protected ModifyParametersPanel modifyParametersPanel;
	
	private SetParameterAction() {
		modifyParametersPanel = AppWindow.getInstance().getModifyParametersPanel();
	}

	/** 
     * sets the parameter entered by the admin in the 
     * application
     * @param e ActionEvent
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
            int i = Integer.parseInt(this.getValue());
            this.setParameter(i);
    		
    		if(i>0) {
    			MainController.getInstance().getSetModifyParametersPanelAction().actionPerformed(e);
    			JOptionPane.showMessageDialog(modifyParametersPanel, "Success setting new parameter ");
    		}else {
    			JOptionPane.showMessageDialog(modifyParametersPanel, 
    					"Incorrect number for parameter, must be positive.");
    		}
    	}catch(NumberFormatException exc){
			JOptionPane.showMessageDialog(modifyParametersPanel, 
					"Incorrect number for parameter, it has to be an integer.");
        }
	}
	
    /**
     * set the parameter in the application
     * @param i value of the parameter
     */
	protected abstract void setParameter(int i);

    /**
     * the value string 
     */
	protected abstract String getValue();
	
}
