package app.gui.view.admin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import app.gui.view.tables.ITabulizable;
import app.gui.view.tables.TablePanel;
import app.theater.events.Event;

/**
 * Class CreateCyclePanel. Panel where the admin adds events to a list in order to create a cycle
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class CreateCyclePanel extends JPanel {
	
	
	private TablePanel eventsPanel;
	
	private JPanel listPanel;
	private JButton removeButton;
	private DefaultListModel<ITabulizable> dataModel;
	private JList<ITabulizable> list;

	
	private JPanel paramPanel;
	private JTextField nameText;
	private JLabel name;
	private JButton createButton;
	
	/**
	 * Constructor of CreateCyclePanel
	 */
	public CreateCyclePanel() {
		this.setLayout(new GridLayout(1,2));
		
		eventsPanel = new TablePanel();
		eventsPanel.setLayout(new BoxLayout(eventsPanel,BoxLayout.Y_AXIS));
		eventsPanel.getSelectButton().setText("Add event to cycle");
		
		listPanel = new JPanel();
		paramPanel = new JPanel();
		
		removeButton = new JButton("Remove");
		nameText = new JTextField(10);
		name = new JLabel("Name: ");
		createButton = new JButton("Create cycle");
		
		setListPanel();
		setParamPanel();
		
		
		this.add(eventsPanel);
		
		
		JPanel aux = new JPanel();
		aux.setLayout(new BoxLayout(aux,BoxLayout.Y_AXIS));
	
		aux.add(listPanel);
		aux.add(Box.createVerticalStrut(30));
		aux.add(paramPanel);
		this.add(aux);
	}
	
	/**
	 * Sets the event list panel
	 */
	private void setListPanel() {
		listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
		dataModel = new DefaultListModel<>(); 
		list= new JList<>(dataModel);	
		
		JScrollPane jsc = new JScrollPane(list);
			
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(8);
			
		
		listPanel.add(jsc);
		listPanel.add(Box.createVerticalStrut(20));
		removeButton.setAlignmentX(CENTER_ALIGNMENT);
		listPanel.add(removeButton);
		
	}
	
	/**
	 * Sets the parameter panel
	 */
	private void setParamPanel() {
		paramPanel.setLayout(new BoxLayout(paramPanel,BoxLayout.Y_AXIS));
		JPanel aux = new JPanel();
		aux.add(name);
		aux.add(nameText);
		
		paramPanel.add(aux);
		createButton.setAlignmentX(CENTER_ALIGNMENT);
		paramPanel.add(createButton);
		paramPanel.add(Box.createVerticalGlue());
	}
	
	/**
	 * Sets the controller for the buttons
	 * @param addAction action to add an event
	 * @param createAction action to create a cycle
	 */
	public void setController(ActionListener addAction,
								ActionListener createAction) {
		eventsPanel.setController(addAction);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeLastSelected();
			}
		});
		createButton.addActionListener(createAction);
		
	}
	
	/**
	 * Adds the element to the list
	 * @param t element to add
	 */
	public void addToList(ITabulizable t) {
		dataModel.addElement(t);
	}
	
	/**
	 * Updates the table of events
	 * @param l list to update the table with
	 */
	public void updateTable(List<? extends ITabulizable> l) {
		eventsPanel.update(l);
	}
	
	/**
	 * Updates the panel
	 */
	public void updatePanel() {
		nameText.setText("");
		dataModel.removeAllElements();
	}
	
	/**
	 * Gets the selected item of the table
	 * @return the selected item
	 * @throws Exception in case no element is selected
	 */
	public ITabulizable getSelectItem() throws Exception {
		return eventsPanel.getSelectedItem();
	}
	
	/**
	 * Gets the data model
	 * @return the data model
	 */
	public DefaultListModel<ITabulizable> getDataModel() {
		return dataModel;
	}
	
	/**
	 * Removes the last element selected
	 */
	public void removeLastSelected() {
		ITabulizable t = list.getSelectedValue();
		if(t!=null) {
			dataModel.removeElement(t);	
		}
	}
	
	/**
	 * Gets the name from the text
	 * @return the name
	 */
	public String getNameText() {
		return nameText.getText();
	}

	/**
	 * Gets the elements of the data model
	 * @return the list with the elements
	 */
	public List<ITabulizable> getElements() {
		List<ITabulizable> ret = new ArrayList<>();
		for(int i = 0; i < dataModel.getSize(); i++) 
			ret.add(dataModel.getElementAt(i));
		return ret;
		
	}
	
	
	
}
