package app.gui.view.areas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Class CreateAreaPanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class CreateAreaPanel extends JPanel {
    private JButton createStandingAreaButton = new JButton("Create");
    private JButton createSittingAreaButton = new JButton("Create");
    private JButton createCompositeAreaButton = new JButton("Create");

    private JLabel labelName = new JLabel("Name: ");
    private final JTextField name = new JTextField(10);

    //sitting
    private JLabel labelRows = new JLabel("Rows: ");
    private final JTextField rows = new JTextField(10);
	private JLabel labelColumns = new JLabel("Columns: ");
    private final JTextField columns = new JTextField(10);

    //standing
    private JLabel labelCapacity = new JLabel("Capacity: ");
    private final JTextField capacity = new JTextField(10);

    /**
     * Constructor of CreateAreaPanel
     */
    public CreateAreaPanel() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        
        JPanel aux = new JPanel();
        aux.add(labelName);
        aux.add(name);
        centerPanel.add(Box.createVerticalStrut(60));
        centerPanel.add(aux);
        this.add(centerPanel);       
        

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Standing Area", tabStandingPanel());
        tabs.addTab("Sitting Area", tabSittingPanel());
        tabs.addTab("Composite Area", tabCompositePanel());
        tabs.setSelectedIndex(1);

        this.add(tabs);
		this.setSize(new Dimension(400,50));
	}

    /**
     * Creates the tab for standing areas
     * @return the tab
     */
    public JPanel tabStandingPanel(){
        JPanel standing = new JPanel();
        
        standing.add(labelCapacity);
        standing.add(capacity);
        standing.add(createStandingAreaButton);
        standing.setPreferredSize(new Dimension(300, 100));

        return standing;
    }

    /**
     * Creates the tab for sitting areas
     * @return the tabo
     */
	public JPanel tabSittingPanel(){
        JPanel sitting = new JPanel();

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2,2));

        center.add(labelRows);
        center.add(rows);
        center.add(labelColumns);
        center.add(columns);

        sitting.add(center);
        sitting.add(createSittingAreaButton);
        sitting.setPreferredSize(new Dimension(300, 100));

        return sitting;
    }

    /**
     * Creates the tab for composite areas
     * @return the tab
     */
    public JPanel tabCompositePanel(){
        JPanel composite = new JPanel();

        composite.add(createCompositeAreaButton);
        composite.setPreferredSize(new Dimension(300, 100));

        return composite;
    }

    /**
     * Sets the controller for the panel
     * @param createStandingAreaAction action listener for the button to create standing areas
     * @param createSittingAreaAction action listener for the button to create sittingg areas
     * @param createCompositeAreaAction action listener for the button to create composite areas
     */
    public void setController(ActionListener createStandingAreaAction, ActionListener createSittingAreaAction, 
    		ActionListener createCompositeAreaAction) {
        createSittingAreaButton.addActionListener(createSittingAreaAction);
        createStandingAreaButton.addActionListener(createStandingAreaAction);
        createCompositeAreaButton.addActionListener(createCompositeAreaAction);
    }

    /**
     * Gets the name of the area 
     * @return the area name
     */
    public String getAreaName() {
		return String.valueOf(this.name.getText());
	}
	
    /**
     * Gets the capacity of the area
     * @return the capacity
     */
	public int getCapacity() {
		try {
			return Integer.parseInt((this.capacity.getText()));
		}catch(NumberFormatException x) {
			return -1;
		}
	}
    
    /**
     * Gets the rows of the area
     * @return the rows
     */
    public int getRows() {
		try {
			return Integer.parseInt((this.rows.getText()));
		}catch(NumberFormatException x) {
			return -1;
		}
	}

    /**
     * Gets the columns of the area
     * @return the columns
     */
    public int getColumns() {
		try {
			return Integer.parseInt((this.columns.getText()));
		}catch(NumberFormatException x) {
			return -1;
		}
	}
}