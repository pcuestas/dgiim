
package app.gui.controller.user.tickets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;

import app.gui.view.user.PurchaseTicketsPanel;
import app.theater.areas.*;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;


/**
 * Class AddTicketsStandingAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddTicketsStandingAction implements ActionListener{


    private AppWindow frame;
    private PurchaseTicketsPanel panel;
    private Performance perf;

	/**
	 * Constructor of AddTicketsStandingAction
	 */
    public AddTicketsStandingAction(){
        frame = AppWindow.getInstance();
        panel = frame.getPurchaseTicketsPanel(); 
    }

    /** 
     * Select standing tickets with the automatic selection
     * @param e action event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		perf = MainController.getInstance().getCurrentPerformance();
		List <Ticket> tickets;
		SimpleArea area;
		try {
			area = (SimpleArea) MainController.getInstance().getTicketsTreeSelection().getAreaSelected();
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(panel, 
					"A simple area has to be selected", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int nTickets=0;
		boolean error = false;

		try {
			nTickets = Integer.parseInt(panel.getStandingText());
		} catch (Exception ex) {
			error = true;
		}

		if (error || nTickets<1) {
			JOptionPane.showMessageDialog(panel, 
					"Invalid number", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		tickets=perf.getAvailableTickets(area);
		int size = tickets.size();
		int add=0;
		
		for(int i=0; add<nTickets && i<size; i++) {
			if(panel.addToList(tickets.get(i)))
				add++;
		}
	        
	    if(add<nTickets) {
	    	JOptionPane.showMessageDialog(panel, 
	    			"Could only add "+add+" tickets of "+nTickets);
	    }	    

	    return;
	}


}