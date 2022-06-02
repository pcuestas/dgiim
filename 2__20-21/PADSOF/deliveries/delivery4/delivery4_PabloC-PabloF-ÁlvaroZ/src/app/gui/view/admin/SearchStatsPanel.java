package app.gui.view.admin;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import app.gui.view.user.*;

/**
 * Class SearchStatsPanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SearchStatsPanel extends JPanel {
    private JButton seeStatsButton = new JButton("See stats");

    private JLabel searchOnlyLabel = new JLabel("Stats of:");
    private final JRadioButton searchOnlyEvent = new JRadioButton("Event");
	private final JRadioButton searchOnlyPerformance = new JRadioButton("Performance");
	private final JRadioButton searchOnlyArea = new JRadioButton("Area");

    private JLabel sortByLabel = new JLabel("Sort by:");
    final JRadioButton sortByRevenue = new JRadioButton("Revenue");
	private final JRadioButton sortByAttendance = new JRadioButton("Attendance");

    private JLabel groupByLabel = new JLabel("Group by:");
    final JRadioButton groupByEvent = new JRadioButton("Event");
	private final JRadioButton groupByPerformance = new JRadioButton("Performance");
	private final JRadioButton groupByArea = new JRadioButton("Area");
	private final JRadioButton noGroup = new JRadioButton("Nothing");

	private JLabel orderByLabel = new JLabel("Order:");
    final JRadioButton orderByHigher = new JRadioButton("Higher to lower");
	private final JRadioButton orderByLower = new JRadioButton("Lower to higher");

	/**
	 * Constructor of SearchStatsPanel
	 */
    public SearchStatsPanel() {
        this.setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        //search only
		searchOnlyEvent.setSelected(true);
        centerPanel.add(SearchPanel.newRadioButtonsPanel(true, searchOnlyLabel,
        		searchOnlyEvent, searchOnlyPerformance,searchOnlyArea));

        //sort by
		sortByRevenue.setSelected(true);
        centerPanel.add(SearchPanel.newRadioButtonsPanel(true, sortByLabel,
        		sortByRevenue, sortByAttendance));

        //group by
        groupByEvent.setSelected(true);
        centerPanel.add(SearchPanel.newRadioButtonsPanel(true, groupByLabel,
        		groupByEvent, groupByPerformance, groupByArea, noGroup));

		//order by
        orderByHigher.setSelected(true);
        centerPanel.add(SearchPanel.newRadioButtonsPanel(true, orderByLabel,
        		orderByHigher, orderByLower));
		
		this.add(seeStatsButton, BorderLayout.SOUTH);

		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Sets the controller for the panel
	 * @param seeStatsAction action listener for the see stats button
	 */
	public void setController(ActionListener seeStatsAction) {
		seeStatsButton.addActionListener(seeStatsAction);
	}

	/**
	 * Getter
	 * @return searchOnlyEvent
	 */
    public JRadioButton getSearchOnlyEvent() {
		return searchOnlyEvent;
	}

	/**
	 * Getter
	 * @return searchOnlyPerformance
	 */
	public JRadioButton getSearchOnlyPerformance() {
		return searchOnlyPerformance;
	}

	/**
	 * Getter
	 * @return sortByRevenue
	 */
	public JRadioButton getSortByRevenue() {
		return sortByRevenue;
	}

	/**
	 * Getter
	 * @return sortByAttendance
	 */
	public JRadioButton getSortByAttendance() {
		return sortByAttendance;
	}

	/**
	 * Getter
	 * @return groupByEvent
	 */
	public JRadioButton getGroupByEvent() {
		return groupByEvent;
	}

	/**
	 * Getter
	 * @return groupByPerformance
	 */
	public JRadioButton getGroupByPerformance() {
		return groupByPerformance;
	}

	/**
	 * Getter
	 * @return groupByArea
	 */
	public JRadioButton getGroupByArea() {
		return groupByArea;
	}
	
	/**
	 * Getter
	 * @return orderByHigher
	 */
	public JRadioButton getOrderByHigher(){
		return orderByHigher;
	}
}
