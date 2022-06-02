package app.gui.view.tables;


import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.*;


/**
 * Class TablePanel. Panel that displays a table of objects that are ITabulizables
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class TablePanel extends JPanel {
    protected JButton selectButton;
    protected List<? extends ITabulizable> list;

    protected JPanel buttonPanel;
    protected JPanel extraPanel = new JPanel();
    protected JTable table;
    protected ActionListener currentActionListener;
    private Vector<Object> columnNames = new Vector<>(Arrays.asList("Title", "Info"));
    private Vector<Object> data = new Vector<>();
    private TableModelNonEditable model = new TableModelNonEditable(data, columnNames);


    /**
    * public constructor
     */
    public TablePanel() {
        this.setLayout(new GridLayout(1,2));
        
		table = new JTable();
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(700, 800));

		JScrollPane scrollBar = new JScrollPane(table);
        selectButton = new JButton("Select");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        selectButton.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalStrut(45));
        buttonPanel.add(selectButton);
        buttonPanel.add(extraPanel);
        
		this.add(scrollBar);
        this.add(buttonPanel);
    }
    
    /**
     * set size fot the table
     * @param x width
     * @param y height
     */
    public void setPreferredSize(int x, int y) {
    	table.setPreferredScrollableViewportSize(new Dimension(x, y));
    }
    

    /**
     * gets the selected item if any (if there are not selcted items it throws an exception)
     * @return the item
     * @throws Exception IndexOutOfBoundsException
     */
	public ITabulizable getSelectedItem() throws Exception{
        int index = this.table.getSelectedRow();
		return this.list.get(index);
	}

    /**
    * Sets the controller for the panel
    * @param action action performed when select button is clicked
     */
    public void setController(ActionListener action){
        if(currentActionListener != null)
            selectButton.removeActionListener(currentActionListener);
        this.currentActionListener = action;
        selectButton.addActionListener(action);
    }

    /**
    * updates the contents of the table
    * @param list list with the contents of the table
     */
    public void update(List<? extends ITabulizable> list){
    	this.list = list;
    	model.setRowCount(0);
    	int size = list.size();

        for(ITabulizable t: list){
            data.add(new Vector<>(Arrays.asList(t.getTitle(), t.getSmallInfo())));
        }

        model.addRow(new Vector<>());
        model.removeRow(size);// this is so the table is correctly updated        
        
        model.fireTableRowsInserted(0, size);// update the table view
    }

    /**
     * The selectButton
     * @return the select button
     */
	public AbstractButton getSelectButton() {
		return selectButton;
	}
    
    
}

