package app.gui.controller.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.trees.AreaInfoPanel;
import app.theater.Application;
import app.theater.areas.SimpleArea;

/**
 * Class AddAnualPriceAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddAnnualPriceAction implements ActionListener {
	
	private AreaInfoPanel areaInfoPanel;
	
	/**
	 * Constructor of AddAnualPriceAction
	 */
	public AddAnnualPriceAction() {
		areaInfoPanel=AppWindow.getInstance().getAreaViewPanel().getAreaInfoPanel();
	}
	
	
    /** 
     * Add an annual price to the selected area
     * @param e action received
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		SimpleArea area = (SimpleArea) MainController.getInstance().getAreasTreeSelection().getAreaSelected();
		double price;

		try {
			price = Double.parseDouble((areaInfoPanel.getPriceText()));
		}catch(NumberFormatException x) {
			JOptionPane.showMessageDialog(areaInfoPanel, "Invalid price format.");
			return;
		}
		
		if(price <= 0) {
			JOptionPane.showMessageDialog(areaInfoPanel, "Invalid price.");
			return;
		}		
		
		area.setAnnualPassPrice(price);
		areaInfoPanel.update(null, Application.getInstance().getAnnualPassPrice(area), null, null);
	}

}
