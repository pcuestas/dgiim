package app.gui.view.things;


import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Class EventPanel. Panel that displays the info for a given event
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class EventPanel extends ThingPanel {

    protected JTextField restrictionText = new JTextField("", 10);
    protected JButton restrictButton = new JButton("Restrict event");
    protected JButton newPerformanceButton = new JButton("Create performance");
    private JButton goSetPrices = new JButton("Set Prices");
	private JTextField newPerformanceText = new JTextField("", 10);

    /**
     * public constructor
     */
    public EventPanel() {
        super("See Performances");
        this.addAdminComponents();
    }

    /**
     * Adds the components for the admin management
     */
    private void addAdminComponents() {
        JPanel restrictPanel = new JPanel();
        restrictPanel.setLayout(new BoxLayout(restrictPanel, BoxLayout.Y_AXIS));
        
        JLabel restrictLabel = new JLabel("New restriction: ");
        JPanel aux1 = new JPanel();
        aux1.add(restrictLabel);
        aux1.add(restrictionText);
        aux1.add(restrictButton);

        JLabel dateLabel = new JLabel("New performance (yyyy-mm-dd): ");
        JPanel aux3 = new JPanel();
        aux3.add(dateLabel);
        aux3.add(newPerformanceText);
        aux3.add(newPerformanceButton);

        JPanel aux2 = new JPanel();
        aux2.add(goSetPrices);

        restrictPanel.add(aux1);
        restrictPanel.add(aux3);
        restrictPanel.add(aux2);

        sideAdminPanel.add(restrictPanel);
    }
    
    /**
     * Clear texts
     */
    public void clear() {
    	newPerformanceText.setText("");
    	restrictionText.setText("");
    }

    /**
     * Sets the controller for the panel
     * 
     * @param selectAction      action performed when select button is clicked
     * @param restrictAction    action performed when restrict button is clicked
     * @param goSetPricesAction action performed when setPrices button is clicked
     * @param newPerformance create new performance
     */
    public void setController(ActionListener selectAction, ActionListener restrictAction,
    		ActionListener goSetPricesAction, ActionListener newPerformance) {
        restrictButton.addActionListener(restrictAction);
        goSetPrices.addActionListener(goSetPricesAction);
        newPerformanceButton.addActionListener(newPerformance);
        super.setController(selectAction);
    }

    /**
     * Gets the restriction
     * 
     * @return the restriction
     */
    public String getModifyText() {
        return this.restrictionText.getText();
    }
    
    /**
     * get the date of the new performance
     * @return a string with the date yyyy-mm-dd
     */
	public String getNewPerformanceDate() {
		return newPerformanceText.getText();
	}
}
