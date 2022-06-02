package app.gui.controller.user;

import java.awt.event.ActionEvent;
import java.util.stream.Collectors;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.user.PurchaseAnnualPassPanel;
import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.passes.AnnualPass;
import app.theater.users.Client;

/**
 * Class SetPurchaseAnnualPassAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SetPurchaseAnnualPassAction extends SetPanelAction {
	private PurchaseAnnualPassPanel purchaseAnnualPassPanel;
	
	/**
	 * Constructor of SetPurchaseAnnualPassAction
	 */
	public SetPurchaseAnnualPassAction() {
		super("purchaseAnnualPassPanel");
	}

	/** 
     * Set the purchase annual pass panel
     * @param e action event
     */
	@Override public void actionPerformed(ActionEvent e) {
		purchaseAnnualPassPanel = AppWindow.getInstance().getPurchaseAnnualPassPanel();
		Client c = (Client) Application.getInstance().getCurrentUser();
		purchaseAnnualPassPanel.update(areasPrices(),
				c.getPasses().stream().
					filter(p -> p instanceof AnnualPass).
					<String>map((p) -> p.toString()).
					collect(Collectors.toList()));
		
		super.actionPerformed(e);
	}

    /** 
     * The formatted string of the prices in each area
     * @return String the formatted string
     */
    private String areasPrices() {
		String s = "<html> Prices in each area:";
		for(SimpleArea a : Application.getInstance().getSimpleAreas() )
			s += "<br>" + "- " + a.getName() + ": " + a.getAnnualPassPrice();
		return s + "</html>";
	}
}
