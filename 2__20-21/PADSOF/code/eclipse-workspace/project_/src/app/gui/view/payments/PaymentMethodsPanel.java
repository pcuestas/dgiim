package app.gui.view.payments;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionListener;

import javax.swing.*;

import app.gui.view.tables.ITabulizable;

/**
 * Class PaymentMethodsPanel. Tabbed Panel with the two methods of payments:
 * card and passes.
 *
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PaymentMethodsPanel extends JPanel {

	private JTabbedPane tabs;

	// PassPayPanel
	private JButton passButton;
	private JList<ITabulizable> list;
	private DefaultListModel<ITabulizable> dataModel = new DefaultListModel<>();
	
	//CardPayPanel
	private JTextField creditText;
	private JButton cardButton;
	
	/**
	* public constructor
	 */
	public PaymentMethodsPanel() {
		tabs = new JTabbedPane();
		tabs.setPreferredSize(new Dimension(500, 300));
		tabs.addTab("Passes", tabPassPay());
		tabs.addTab("Card", tabCardPay());
		tabs.setSelectedIndex(1);

		this.add(tabs);

	}

	/**
	 * sets the tab for the passPay
	 * @return the tab	
	 */
	private JPanel tabPassPay(){
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      JPanel aux1 = new JPanel();
      
      JLabel label = new JLabel("Select a pass");
      passButton = new JButton("Pay");
    
      aux1.add(label);
      aux1.add(passButton);
      
      dataModel = new DefaultListModel<ITabulizable>(); 
	  list= new JList<ITabulizable>(dataModel);	
		
	  JScrollPane jsc = new JScrollPane(list);
			
	  list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	  list.setLayoutOrientation(JList.VERTICAL);
	  list.setVisibleRowCount(8);
	  
	  panel.add(jsc);
	  panel.add(aux1);
      return panel;
  	}

	/**
	 * sets the tab for the card pay
	 * @return the tab
	 */
	private JPanel tabCardPay() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel aux = new JPanel();
		
		JLabel label = new JLabel("Credit card number");
		creditText = new JTextField(16);
		cardButton = new JButton("Pay");
		
		aux.add(label);
		aux.add(creditText);
		aux.add(cardButton);
		
		panel.add(aux, BorderLayout.CENTER);
		creditText.setLocation(200, 200);
		return panel;
	}

	/**
	 * sets the preferred size for the tabs
	 * @param x width
	 * @param y height
	 */
	public void setPreferredSize(int x, int y) {
		tabs.setPreferredSize(new Dimension(x, y));
	}

	/**
	 * adds and element to the table
	 * @param item item to add
	 */
	public void addToList(ITabulizable item) {
		dataModel.addElement(item);
	}
	
	/**
	* Sets the controller for the panel
	* @param cardAction action performed when pay button is clicked in tab cardPay
	* @param passAction action performed when pay button is clicked in passTab
	 */
	public void setController(ActionListener cardAction, ActionListener passAction) {
		passButton.addActionListener(passAction);
		cardButton.addActionListener(cardAction);
	}
	
	/**
	* updates the panel
	* @param list list of passes of the user
	 */
	public void update(List <? extends ITabulizable> list) {
		clear();
		dataModel.removeAllElements();
		for(ITabulizable item: list) {
			dataModel.addElement(item);
		}
	}
	
	/**
	* clears the JTextFields
	 */
	public void clear() {
		creditText.setText("");
	}

	/**
	 * Gets the pass selected
	 * @return the pass
	 */
	public ITabulizable getPassSelected() {
		return list.getSelectedValue();
	}

	/**
	 * Gets the credit card 
	 * @return the credit card
	 */
	public String getCreditCard() {
		return creditText.getText();
	}
	
}
