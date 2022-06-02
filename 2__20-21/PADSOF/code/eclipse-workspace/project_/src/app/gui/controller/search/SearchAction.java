package app.gui.controller.search;

import java.awt.event.*;
import java.util.List;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.tables.PerformancesTablePanel;
import app.gui.view.tables.TablePanel;
import app.gui.view.user.SearchPanel;
import app.theater.*;
import app.theater.events.*;
import app.theater.performances.*;
import app.theater.searches.*;

/**
 * Class SearchAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SearchAction implements ActionListener{
	private AppWindow frame;
	private SearchPanel searchPanel;
    private TablePanel tablePanel;
    private PerformancesTablePanel performancesTablePanel;

    /**
     * Constructor of SearchAction
     */
	public SearchAction() {
		this.frame = AppWindow.getInstance();
		this.searchPanel = frame.getSearchPanel();
        tablePanel = frame.getTablePanel();
        performancesTablePanel = frame.getPerformancesTablePanel();
	}

    /** 
     * perform the search and display results
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Application app = Application.getInstance();
		String input = searchPanel.getInput();

        SearchBy searchBy = getSearchBy();
		SearchOnly searchOnly = getSearchOnly();


		if(searchPanel.getSearchEvents().isSelected()){
			List<Event> resultEvents = app.searchEvents(searchBy, searchOnly, input);
            
            tablePanel.update(resultEvents);
            tablePanel.setController(MainController.getInstance().getSetEventPanelAction());
            frame.showPanel("tablePanel");
		}else{
			List<Performance> resultPerformances  = app.searchPerformances(searchBy, searchOnly, input); 
            
            MainController.getInstance().setResultPerformances(resultPerformances);
			performancesTablePanel.update(resultPerformances);
            performancesTablePanel.setController(MainController.getInstance().getSetPerformancePanelAction());
            frame.showPanel("performancesTablePanel");
		}
		
	}



    /** 
     * Get the search only parameter of the search
     * @return SearchOnly
     */
	private SearchOnly getSearchOnly(){
		if(searchPanel.getSearchOnlyConcert().isSelected()){
             return SearchOnly.CONCERT;
        }
        
        if(searchPanel.getSearchOnlyPlay().isSelected()){
            return SearchOnly.PLAY;
        }

        if(searchPanel.getSearchOnlyDance().isSelected()){
            return SearchOnly.DANCE;
        }
        // search by title
            return SearchOnly.ALL;
    }

    /** 
     * Gets the type of search (author, director or title)
     * @return the type
     */
    private SearchBy getSearchBy(){
		if(searchPanel.getSearchByAuthor().isSelected()){
             return SearchBy.AUTHOR;
        }
        
        if(searchPanel.getSearchByDirector().isSelected()){
            return SearchBy.DIRECTOR;
        }
        // search by title
            return SearchBy.TITLE;
    }

}
