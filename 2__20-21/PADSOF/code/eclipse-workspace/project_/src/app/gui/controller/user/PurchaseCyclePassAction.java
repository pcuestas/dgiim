package app.gui.controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.PurchaseCyclePassPanel;
import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.events.cycles.Cycle;
import app.theater.paymentmethod.CreditCard;
import app.theater.users.Client;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

/**
 * Class PurchaseCyclePassAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PurchaseCyclePassAction implements ActionListener {

	private PurchaseCyclePassPanel purchaseCyclePassPanel;
	private JTree tree;
	
	/**
	 * Constructor of PurchaseCyclePassAction
	 */
	public PurchaseCyclePassAction() {
		purchaseCyclePassPanel = AppWindow.getInstance().getPurchaseCyclePassPanel();
		tree = purchaseCyclePassPanel.getTree();
	}
	
    /** 
     * Purchase the cycle pass
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		SimpleArea a ;
		try {
			a = (SimpleArea)Application.getInstance().getArea((String)(selectedNode.getUserObject()));
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(purchaseCyclePassPanel,
					"You have to select a simple area", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String card = purchaseCyclePassPanel.getCard();
		if (!TeleChargeAndPaySystem.isValidCardNumber(card)){
			JOptionPane.showMessageDialog(purchaseCyclePassPanel,
					"Invalid card number", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
        }
        Cycle cycle = MainController.getInstance().getCurrentCycle();
        if(cycle == null) {
			JOptionPane.showMessageDialog(purchaseCyclePassPanel,
					"You need to select a cycle", 
					"Error", JOptionPane.ERROR_MESSAGE);
        	return ;
        }
		if(!((Client)Application.getInstance().getCurrentUser()).buyPass(cycle, a, new CreditCard(card))) {
			JOptionPane.showMessageDialog(purchaseCyclePassPanel,
					"Operation could not be performed", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}else {
			JOptionPane.showMessageDialog(purchaseCyclePassPanel,
					"Cycle Pass purchased: " + cycle.getName());
		}
		MainController.getInstance().getSetPurchaseCyclePassAction().actionPerformed(e);
	}

}
