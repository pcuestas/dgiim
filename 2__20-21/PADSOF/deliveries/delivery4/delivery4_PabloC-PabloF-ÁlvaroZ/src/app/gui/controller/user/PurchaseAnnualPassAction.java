package app.gui.controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.PurchaseAnnualPassPanel;
import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.paymentmethod.CreditCard;
import app.theater.users.Client;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

/**
 * Class PurchaseAnnualPasAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PurchaseAnnualPassAction implements ActionListener {

	private PurchaseAnnualPassPanel purchaseAnnualPassPanel;
	private JTree tree;

	/**
	 * Constructor of PurchaseAnnualPassAction
	 */
    public PurchaseAnnualPassAction() {
        purchaseAnnualPassPanel = AppWindow.getInstance().getPurchaseAnnualPassPanel();
        tree = purchaseAnnualPassPanel.getTree();

    }

    /**
     * Purchase an annual pass
     * 
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        SimpleArea a;
        try {
            a = (SimpleArea) Application.getInstance().getArea((String) (selectedNode.getUserObject()));
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(purchaseAnnualPassPanel, "You have to select a simple area", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int year;
        try {
            year = Integer.parseInt(purchaseAnnualPassPanel.getYear());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(purchaseAnnualPassPanel, "You need to enter a number", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String card = purchaseAnnualPassPanel.getCard();
        if (!TeleChargeAndPaySystem.isValidCardNumber(card)) {
            JOptionPane.showMessageDialog(purchaseAnnualPassPanel, "Invalid card number", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!((Client) Application.getInstance().getCurrentUser()).buyPass(year, a, new CreditCard(card))) {
            JOptionPane.showMessageDialog(purchaseAnnualPassPanel, "Operation could not be performed", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        MainController.getInstance().getSetPurchaseAnnualPassAction().actionPerformed(e);
    }

}
