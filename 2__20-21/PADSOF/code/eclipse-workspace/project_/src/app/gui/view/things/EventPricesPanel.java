package app.gui.view.things;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

/**
 * Class EventPricesPanel. Panel that allows to set prices of an event for all areas
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class EventPricesPanel extends JPanel {
	protected JPanel areaInfoPanel;
	
	protected JLabel priceLabel;
	protected JTextField priceText;
	protected JButton addPriceButton;
	private JTree tree;
	
	/**
	* public constructor
	* @param dataModel treeModel with the areas of the theater
	 */
	public EventPricesPanel(DefaultTreeModel dataModel) {
		
		tree = new JTree (dataModel);
		JScrollPane scrollbar = new JScrollPane(tree);
		scrollbar.setViewportView(tree);
		scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
	
		// Add the scroll bar
		JPanel treeExample = new JPanel();
		treeExample.add(scrollbar);
			
		
		this.setLayout(new GridLayout(2,1));
		areaInfoPanel = new JPanel();
		
		priceLabel= new JLabel("Price: ");
		priceText = new JTextField(10);
		addPriceButton = new JButton ("Add price");
		
		areaInfoPanel.add(priceLabel);
		areaInfoPanel.add(priceText);
		areaInfoPanel.add(addPriceButton);
		
		this.add(treeExample);
		this.add(areaInfoPanel);
		areaInfoPanel.setVisible(false);
	}
	
	/**
	* @param act action performed when add button is clicked
	* @param listener listener for the tree of the panel
	 */
	public void setController(ActionListener act, TreeSelectionListener listener) {
		addPriceButton.addActionListener(act);
		tree.addTreeSelectionListener(listener);
	}
	
	/**
	* updates the JLabel of the panel
	* @param price price to update with
	 */
	public void update(double price) {
		priceLabel.setText("Price: " + (price < 0 ? "not established" : price));
		priceText.setText("");
	}

	/**
	* sets the simplePanel visible iff b is true
	* @param b decides if it shows the panel or not
	 */
	public void setVisibilitySimplePanel(boolean b) {
		areaInfoPanel.setVisible(b);
	}
	
	/**
	 * Gets the price
	 * @return the price
	 */
	public String getPriceText() {
		return priceText.getText();
	}

	/**
	 * Gets the tree
	 * @return the tree
	 */
	public JTree getTree() {return tree;}
	
}
