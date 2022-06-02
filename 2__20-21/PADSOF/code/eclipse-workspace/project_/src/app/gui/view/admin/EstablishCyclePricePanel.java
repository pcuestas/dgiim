package app.gui.view.admin;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import app.gui.view.things.EventPricesPanel;

/**
 * Class EstablishCyclePanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class EstablishCyclePricePanel extends EventPricesPanel{
	
	private JButton createCycleButton;
	
	/**
	 * Constructor of EstablishCyclePricePanel
	 * @param dataModel the data model for the panel
	 */
	public EstablishCyclePricePanel(DefaultTreeModel dataModel) {
		super(dataModel);
		createCycleButton = new JButton("Create Cycle");
		this.areaInfoPanel.add(createCycleButton);
		this.addPriceButton.setText("Add reduction");
		this.priceLabel.setText("Reduction: ");
	}
	
	/**
	 * Sets the controller for the panel
	 * @param addPriceAction action to add a price
	 * @param createAction action to create the cycle
	 * @param listener tree listener
	 */
	public void setController(ActionListener addPriceAction, ActionListener createAction,
								TreeSelectionListener listener) {
		super.setController(addPriceAction, listener);
		createCycleButton.addActionListener(createAction);
	}
	
	/**
	 * Update the panel
	 * @param red reduction
	 */
	@Override
	public void update(double red) {
		priceLabel.setText("Reduction(%): " + (red < 0 ? "not established" : red));
		priceText.setText("");
	}
}
