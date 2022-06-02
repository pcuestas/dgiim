package app.gui.controller.admin.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.EventPricesPanel;
import app.theater.Application;
import app.theater.areas.*;
import app.theater.events.Event;

/**
 * Class AddEventPricesAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddEventPricesAction implements ActionListener {
	private EventPricesPanel panel;
	private AppWindow frame;
	private JTree tree;
	
	/**
	 * Constructor of AddEventPricesAction
	 */
	public AddEventPricesAction() {
		frame=AppWindow.getInstance();
		panel=frame.getEventPricesPanel();
		tree=panel.getTree();
	}
	
    /** 
	 * Add prices to an event when an action is received
     * @param e action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode lastNode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        Area a = Application.getInstance().getArea((String)(lastNode.getUserObject()));
        
        
		Event event = MainController.getInstance().getCurrentEvent();
		Double price;
		
		if(a instanceof CompositeArea) {
			JOptionPane.showMessageDialog(panel,"Prices are only for SimpleAreas");
			return;
		}
		
		try {
			price = Double.parseDouble(panel.getPriceText());
		}catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(panel,"Invalid price format");
			return;
		}
		
		if(price<0) {
			JOptionPane.showMessageDialog(panel,"Price must be greater than 0 ");
			return;
		}
		
		if(event.addPrice((SimpleArea)a, price)) {
			JOptionPane.showMessageDialog(panel,"Price added");
			panel.update(price);
		}else {
			JOptionPane.showMessageDialog(panel,"Could not add prices becuase there are future performances ");
		}
		
		
		
	}

}
