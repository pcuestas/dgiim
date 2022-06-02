package app.gui.controller.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.tables.TablePanel;
import app.gui.view.user.PurchaseCyclePassPanel;
import app.theater.Application;
import app.theater.events.cycles.Cycle;
import app.theater.passes.CyclePass;
import app.theater.users.Client;

/**
 * Class SetPurchaseCyclePassAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetPurchaseCyclePassAction extends SetPanelAction {
	private PurchaseCyclePassPanel purchaseCyclePassPanel;

	/**
	 * Constructor of SetPurchaseCyclePassAction
	 */
	public SetPurchaseCyclePassAction() {
		super("purchaseCyclePassPanel");
	}
	/** 
     * Set the purchase cycle pass panel
     * @param e action event
     */
	@Override public void actionPerformed(ActionEvent e) {
		purchaseCyclePassPanel = AppWindow.getInstance().getPurchaseCyclePassPanel();
		Client c = (Client) Application.getInstance().getCurrentUser();
		//purchaseCyclePassPanel.update();
		TablePanel tablePanel = purchaseCyclePassPanel.getCyclesTable();
		tablePanel.update(Application.getInstance().getCycles());
		tablePanel.setController(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				Cycle c;
				try {
					c = (Cycle)tablePanel.getSelectedItem();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(purchaseCyclePassPanel,
							"You have to select a cycle", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				MainController.getInstance().setCurrentCycle(c);
				purchaseCyclePassPanel.getCyclePanel().update(c, false);
				purchaseCyclePassPanel.getCyclePanel().setVisible(true);
			}			
		});
        purchaseCyclePassPanel.update(
				c.getPasses().stream().
					filter(p -> p instanceof CyclePass).
					<String>map(p -> p.toString()).
					collect(Collectors.toList()));
		tablePanel.setVisible(true);
		
		super.actionPerformed(e);
	}
}
