package app.gui.controller.user.tickets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.Util;
import app.gui.view.user.PurchaseTicketsPanel;
import app.theater.areas.SimpleArea;
import app.theater.areas.SittingArea;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;

/**
 * Class AddTicketsSeatedAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddTicketsSeatedAction implements ActionListener {
	
	
	private AppWindow frame;
	private PurchaseTicketsPanel panel;
	private Performance perf;

	/**
	 * Constructor of AddTicketsSeatedAction
	 */
	public AddTicketsSeatedAction() {
		frame = AppWindow.getInstance();
		panel = frame.getPurchaseTicketsPanel();
	}

    /** 
     * Select sitting tickets with the automatic selection
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		perf = MainController.getInstance().getCurrentPerformance();
        
		SittingArea area = (SittingArea) MainController.getInstance().getTicketsTreeSelection().getAreaSelected();

		for(Util.SittingPair i: panel.getIndexes()){
			Ticket ticket = perf.getSittingTicket(area, i.getRow(), i.getColumn());
		    panel.addToList(ticket);
		}

		MainController.getInstance().getTicketsTreeSelection().valueChanged(null);
	}

}
