package app.gui.controller.admin.cycles;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.admin.CreateCyclePanel;
import app.gui.view.admin.EstablishCyclePricePanel;
import app.gui.view.tables.ITabulizable;
import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.events.Event;
import app.theater.events.cycles.Cycle;

/**
 * Class AddCycleAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddCycleAction implements ActionListener {
	
	private EstablishCyclePricePanel panel;
	private JTree tree;
	private CreateCyclePanel createCyclePanel;
	
    /**
     * Contructor of AddCycleAction
     */
	public AddCycleAction() {
		panel = AppWindow.getInstance().getEstablishCyclePricePanel();
		createCyclePanel = AppWindow.getInstance().getCreateCyclePanel();
		tree=panel.getTree();
	}
	
	
    /** 
	 * Adds a new cycle when an action event is received
     * @param e the event received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		boolean error=false;
		Map <SimpleArea, Double> map = MainController.getInstance().
											getCreateCycleAction().getMap();
		
		for(SimpleArea a: map.keySet()) {
			if(map.get(a)<0) {
				error=true;
				break;
			}
		}
		
		if(error) {
			JOptionPane.showMessageDialog(panel,
					"Add reductions for all areas", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String name = MainController.getInstance().getCreateCycleAction().getName();
		List <ITabulizable> list = createCyclePanel.getElements();
		List<Event> events = new ArrayList<>();
		for(ITabulizable t: list) events.add((Event)t);
		
		if(!(Application.getInstance().addCycle(new Cycle(name, events, map)))) {
			JOptionPane.showMessageDialog(panel,
					"Cycle could not be added. All events have to have prices for all areas.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		AppWindow.getInstance().showPanel("adminHomePanel");
	}
}
