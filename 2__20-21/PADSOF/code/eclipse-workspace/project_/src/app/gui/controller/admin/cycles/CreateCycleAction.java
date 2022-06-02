package app.gui.controller.admin.cycles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import app.gui.view.AppWindow;
import app.gui.view.admin.CreateCyclePanel;
import app.gui.view.tables.ITabulizable;
import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.events.Event;
import app.theater.events.cycles.Cycle;

/**
 * Class CreateCycleAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CreateCycleAction implements ActionListener {
	
	private CreateCyclePanel panel;
	private List<ITabulizable> list;
	private Map<SimpleArea,Double> map;
	private String name;
	
	/**
	 * Constructor of CreateCycleAction
	 */
	public CreateCycleAction() {
		panel = AppWindow.getInstance().getCreateCyclePanel();
	}
	
	
    /** 
	 * Creates a cycle when an action is received
     * @param e action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		name = panel.getNameText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(panel,
					"Provide a name", "Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		List <ITabulizable> events = panel.getElements();
		int size = events.size();
		
		if(size==0) {
			JOptionPane.showMessageDialog(panel,
					"Select one event at least", "Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Map<SimpleArea,Double> reductions = new HashMap<>();
		for(SimpleArea a: Application.getInstance().getSimpleAreas()) {
			reductions.put(a,-1.0);
		}
		map=reductions;
		
		AppWindow.getInstance().showPanel("establishCyclePricePanel");

	}
	
	
    /** 
     * The map of reductions
     * @return Map<SimpleArea, Double>
     */
    public Map<SimpleArea,Double> getMap(){
		return map;
	}
	
	
    /** 
     * the name of the cycle to be created
     * @return String the name
     */
    public String getName() {
		return name;
	}
	
	

}
