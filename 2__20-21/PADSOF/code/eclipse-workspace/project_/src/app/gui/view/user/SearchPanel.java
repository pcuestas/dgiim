package app.gui.view.user;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Class SearchPanel. This class has the menu for a search.
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SearchPanel extends JPanel {
    private JButton searchButton = new JButton("Search");
    
    private final JTextField input = new JTextField(30);

    private JLabel searchByLabel = new JLabel("Search by:");
    final JRadioButton searchByTitle = new JRadioButton("Title");
	private final JRadioButton searchByAuthor = new JRadioButton("Author");
	private final JRadioButton searchByDirector = new JRadioButton("Director");

    private JLabel searchOnlyLabel = new JLabel("Type of event:");
    private final JRadioButton searchOnlyAll = new JRadioButton("All");
	private final JRadioButton searchOnlyConcert = new JRadioButton("Concert");
	private final JRadioButton searchOnlyPlay = new JRadioButton("Play");
	private final JRadioButton searchOnlyDance = new JRadioButton("Dance");

    private JLabel searchType = new JLabel("Event/Performances:");
    private final JRadioButton searchEvents = new JRadioButton("Events");
	private final JRadioButton searchPerformances = new JRadioButton("Performances");


	/**
	 * public constructor
	 */
	public SearchPanel() {
        this.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        //search only
		searchOnlyAll.setSelected(true);
        centerPanel.add(newRadioButtonsPanel(true, searchOnlyLabel,
        		searchOnlyAll, searchOnlyConcert,searchOnlyPlay, searchOnlyDance));

        //search by
		searchByTitle.setSelected(true);
        centerPanel.add(newRadioButtonsPanel(true, searchByLabel,
        		searchByTitle, searchByAuthor, searchByDirector));

        //events or performances
        searchEvents.setSelected(true);
        centerPanel.add(newRadioButtonsPanel(true, searchType,
        		searchEvents, searchPerformances));
		
		northPanel.add(input);
		northPanel.add(searchButton);
		this.add(northPanel, BorderLayout.NORTH);

		this.add(centerPanel, BorderLayout.CENTER);
	}
	

	/**
	 * updates the panel
	 */
	public void update() {
		input.setText("");
	}
	

	/**
	 * @param searchAction action performed when search button is clicked
	 */
	public void setController(ActionListener searchAction) {
		searchButton.addActionListener(searchAction);
	}


	/********** GETTERS *******************/
    /**
     * Getter
     * @return SearchByTitle
     */
    public JRadioButton getSearchByTitle() {
		return searchByTitle;
	}

	
    /** 
     * Getter
     * @return SearchByAuthor
     */
    public JRadioButton getSearchByAuthor() {
		return searchByAuthor;
	}

	
    /** 
     * Getter
     * @return SearchByDirector
     */
    public JRadioButton getSearchByDirector() {
		return searchByDirector;
	}

	
    /** 
     * Getter
     * @return SearchOnlyAll
     */
    public JRadioButton getSearchOnlyAll() {
		return searchOnlyAll;
	}

	
    /** 
     * Getter
     * @return SearchOnlyConcert
     */
    public JRadioButton getSearchOnlyConcert() {
		return searchOnlyConcert;
	}

	
    /** 
     * Getter
     * @return SearchOnlyPlay
     */
    public JRadioButton getSearchOnlyPlay() {
		return searchOnlyPlay;
	}

	
    /** 
     * Getter
     * @return SearchOnlyDance
     */
    public JRadioButton getSearchOnlyDance() {
		return searchOnlyDance;
	}

	
    /** 
     * Getter
     * @return SearchPerformances
     */
    public JRadioButton getSearchPerformances() {
		return searchPerformances;
	}

	
    /** 
     * Getter
     * @return the input
     */
    public String getInput(){ 
        return new String(input.getText()); 
    }
	
	
    /** 
     * Getter
     * @return SearchEvents
     */
    public JRadioButton getSearchEvents(){
		return this.searchEvents;
	}
	
    
	/**
	* Creates a JPanel with a radioButton
	* @param withBlackBorder true if the panel is desired to have border
	* @param label Label of the panels
	* @param buttons array of buttons to be grouped
	* @return the new panel
	 */
    public static JPanel newRadioButtonsPanel(boolean withBlackBorder, JLabel label, JRadioButton ... buttons) {
    	ButtonGroup group = new ButtonGroup();
    	JPanel panel = new JPanel();
    	panel.add(label);
    	for (JRadioButton b:buttons) {
    		group.add(b);
    		panel.add(b);
    	}
    	if(withBlackBorder)
    		panel.setBorder(BorderFactory.createLineBorder(Color.black));
    	return panel;
    }
}
