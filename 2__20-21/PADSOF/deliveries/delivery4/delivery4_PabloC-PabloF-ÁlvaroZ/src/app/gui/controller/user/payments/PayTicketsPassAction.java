package app.gui.controller.user.payments;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.tables.ITabulizable;
import app.gui.view.user.PurchaseTicketsPanel;
import app.theater.passes.Pass;
import app.theater.paymentmethod.PassPay;
import app.theater.performances.tickets.Ticket;

/**
 * Class PayTicketsPassAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PayTicketsPassAction implements ActionListener {
	PurchaseTicketsPanel purchaseTicketsPanel;
	
	/**
	 * Constructor of PayTicketsPassAction
	 */
	public PayTicketsPassAction() {
		purchaseTicketsPanel = AppWindow.getInstance().getPurchaseTicketsPanel();
	}
    /** 
     * Pay for tickets with a pass 
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<? extends ITabulizable> tickets = purchaseTicketsPanel.getElements();
		
		int size = tickets.size();
		if(size != 1) {
			String s;
			if(size > 1) 
				s = "Only one ticket can be purchased with a pass in one performance";
			else 
				s = "A ticket has to be selected";
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					s, "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		Pass pass;
		try {
			pass = purchaseTicketsPanel.getPassSelected();
		}catch(Exception exc) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"A pass has to be selected", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		if(pass == null) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"A pass has to be selected", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		boolean flag = ((Ticket)(tickets.get(0))).purchase(
				MainController.getInstance().getCurrentClient(), 
				new PassPay(pass));
		
		if (flag) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"Ticket purchased!");
			purchaseTicketsPanel.clear();
			purchaseTicketsPanel.clearList();
			MainController.getInstance().getTicketsTreeSelection().valueChanged(null);
		} else {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"Could not purchase the ticket. The pass is not valid.", 
					"Error", JOptionPane.ERROR_MESSAGE);			
		}
	}

}
