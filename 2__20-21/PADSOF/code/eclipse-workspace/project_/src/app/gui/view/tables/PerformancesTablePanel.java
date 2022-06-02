package app.gui.view.tables;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class PerformancesTablePanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class PerformancesTablePanel extends TablePanel {
	
	private JPanel searchPanel;
    private JLabel filterLabel;
    private JTextField beginText;
    private JTextField endText;
    private JButton filterButton;
    private JButton showAllButton;
    
    /**
     * Constructor
     */
    public PerformancesTablePanel() {
    	super();
    	setSearchPanel();
    	extraPanel.add(searchPanel);
    }
    
    private void setSearchPanel() {
		searchPanel = new JPanel(new GridLayout(3,1));
		
		filterLabel = new JLabel("Filter performances in an interval of time (yyyy-mm-dd)");
		filterButton = new JButton("Filter");
		showAllButton = new JButton("Show all");
	    beginText = new JTextField(10);
	    endText = new JTextField(10);
	    
	    JPanel aux0 = new JPanel();
	    aux0.add(filterLabel);
	    searchPanel.add(aux0);
	    
	    JPanel aux = new JPanel();
	    aux.add(beginText);
	    aux.add(endText);
	    searchPanel.add(aux);
	    
	    JPanel aux2 = new JPanel();
	    aux2.add(filterButton);
	    aux2.add(showAllButton);
	    searchPanel.add(aux2);
	    
	    this.add(searchPanel);
	}
    
    /**
     * set controllers
     * @param filterAction filter performances
     * @param showAllAction show all performances action
     */
    public void setController(ActionListener filterAction, ActionListener showAllAction) {
        filterButton.addActionListener(filterAction);
        showAllButton.addActionListener(showAllAction);
    }
    
    
    /**
     * 
     * @return text of the begin TextField
     */
    public String getBeginText() {
    	return beginText.getText();
    }
    
    /**
     * 
     * @return text of the end TextField
     */
    public String getEndText() {
    	return endText.getText();
    }
    
    /**
     * updates the contents of the table
     * @param list list with the contents of the table
      */
     public void update(List<? extends ITabulizable> list){
    	 super.update(list);
    	 clear();
     }
	
    /**
     * clears the JTextFields of the panel
     */
    public void clear() {
    	beginText.setText("");
    	endText.setText("");
    }
}
