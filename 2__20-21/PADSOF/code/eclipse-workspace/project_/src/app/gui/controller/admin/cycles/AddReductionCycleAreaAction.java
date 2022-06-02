package app.gui.controller.admin.cycles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.admin.EstablishCyclePricePanel;
import app.theater.Application;
import app.theater.areas.Area;
import app.theater.areas.SimpleArea;
import app.theater.util.Util;

/**
 * Class AddReductionCycleAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddReductionCycleAreaAction implements ActionListener {
	
	private EstablishCyclePricePanel panel;
	private JTree tree;
	
    /**
     * Constructor of AddReductionCycleAreaAction
     */
	public AddReductionCycleAreaAction() {
		panel = AppWindow.getInstance().getEstablishCyclePricePanel();
		tree=panel.getTree();
	}
	
	
	
    /** 
	 * Adds a reduction to an area for the cycle when an action is received
     * @param e the action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		boolean error=false;
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		SimpleArea a = (SimpleArea)Application.getInstance().getArea((String)(selectedNode.getUserObject()));
		
		
		Map <SimpleArea,Double> res=MainController.getInstance().
									getCreateCycleAction().getMap();
		
		Double r=0.0;
		try {
			r=Double.parseDouble(panel.getPriceText());
		}catch(NumberFormatException ex) {
			error=true;
		}
		
		if(error || !Util.isPercentage(r)) {
			JOptionPane.showMessageDialog(panel,
					"Invalid reduction", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		res.put(a, r);
		panel.update(r);
	}
	
	

}
