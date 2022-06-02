package app.gui.controller.admin;

import java.awt.event.*;
import java.util.List;
import app.gui.view.AppWindow;
import app.gui.view.tables.TablePanel;
import app.gui.view.admin.SearchStatsPanel;
import app.theater.*;
import app.theater.stats.*;

/**
 * Class StatsAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class StatsAction implements ActionListener{
	private AppWindow frame;
	private SearchStatsPanel searchStatsPanel;
    private TablePanel statsTablePanel;

    /** 
     * Constructor of StatsAction
     */
	public StatsAction() {
		this.frame = AppWindow.getInstance();
		this.searchStatsPanel = frame.getSearchStatsPanel();
        statsTablePanel = frame.getStatsTablePanel();
	}

	
    /** 
     * displays the selected stats
     * @param e ActionEvent
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		Application app = Application.getInstance();

        StatComparator sort;

        if(searchStatsPanel.getSortByRevenue().isSelected()) sort = StatComparator.REVENUE;
        else sort = StatComparator.ATTENDANCE;

        StatComparator group = null;

        if(searchStatsPanel.getGroupByEvent().isSelected()) group = StatComparator.EVENT;
        else if (searchStatsPanel.getGroupByPerformance().isSelected()) group = StatComparator.PERFORMANCE;
        else if (searchStatsPanel.getGroupByArea().isSelected()) group = StatComparator.AREA;

        boolean order;

        if(searchStatsPanel.getOrderByHigher().isSelected()) order = true;
        else order = false;
        
        List<Stat> stats;

		if(searchStatsPanel.getSearchOnlyEvent().isSelected()){
			stats = app.getStatsEvents();
		}else if (searchStatsPanel.getSearchOnlyPerformance().isSelected()){
			stats  = app.getSpecificStats(); 
		}else{
            stats = app.getStatsAreas();
        }
		
        Stat.sortStats(stats, sort, order);
        if(group!=null){
        	Stat.sortStats(stats, group, false);
        }

        statsTablePanel.update(stats);
        frame.showPanel("statsTablePanel");
	}
}
