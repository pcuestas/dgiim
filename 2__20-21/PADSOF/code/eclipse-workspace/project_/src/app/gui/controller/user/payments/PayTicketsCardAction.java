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
import app.theater.paymentmethod.CreditCard;
import app.theater.paymentmethod.PassPay;
import app.theater.performances.tickets.Ticket;

/**
 * Class PayTicketsCardAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PayTicketsCardAction implements ActionListener {
	PurchaseTicketsPanel purchaseTicketsPanel;
	
	/**
	 * Constructor of PayTicketsCardAction
	 */
	public PayTicketsCardAction() {
		purchaseTicketsPanel = AppWindow.getInstance().getPurchaseTicketsPanel();
	}
	
    /** 
     * Pay for tickets with a card 
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		List<? extends ITabulizable> tickets = purchaseTicketsPanel.getElements();
		
		int size = tickets.size();
		if(size == 0) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"At least one ticket has to be selected", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		int count = 0;
		CreditCard card = new CreditCard(purchaseTicketsPanel.getCreditCard());
		for (ITabulizable t:tickets) {
			boolean flag = ((Ticket)(t)).purchase(
					MainController.getInstance().getCurrentClient(), card);
			if(flag) count++;
			else break;
		}
		
		if (count == 0){
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"No tickets could be purchased.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		if (count < size) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"Only "+count+" tickets could be purchased out of the "+size+" selected");
		}else if (count == size) {
			JOptionPane.showMessageDialog(purchaseTicketsPanel,
					"The "+size+" selected tickets were purchased");
		}
		purchaseTicketsPanel.clear();
		purchaseTicketsPanel.clearList();
		MainController.getInstance().getTicketsTreeSelection().valueChanged(null);
	}

}
