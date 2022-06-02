package app.gui.view.trees;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;

/**
 * Class AreaViewPanel
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class AreaViewPanel extends JPanel {
	AreaTreePanel areaTreePanel;
	AreaInfoPanel areaInfoPanel;
	
    /**
     * Constructor 
     */
	public AreaViewPanel() {
		areaTreePanel= new AreaTreePanel();
		areaInfoPanel = new AreaInfoPanel();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(areaTreePanel);
		this.add(areaInfoPanel);
	}
	
	
    /** 
     * set the controller 
     * @param removeAct remove area
     * @param addAct add area
     * @param treeList tree listener
     * @param selectSeatAction select seat
     * @param disableAction disable seat action 
     * @param addAnnualPriceAction add annual pass price
     */
    public void setController(ActionListener removeAct, ActionListener addAct, TreeSelectionListener treeList,
			ActionListener selectSeatAction,ActionListener disableAction, ActionListener addAnnualPriceAction) {
		areaTreePanel.setController(treeList);
		areaInfoPanel.setController(removeAct, addAct,selectSeatAction,disableAction,addAnnualPriceAction);
	}
	
	/**
     * get the area tree panel
     * @return area tree panel
     */
	public AreaTreePanel getAreaTreePanel(){return areaTreePanel;}
	/**
     * get the area info panel
     * @return area info panel
     */
	public AreaInfoPanel getAreaInfoPanel(){return areaInfoPanel;}

    /**
     * reload the area tree panel
     */
	public void reload() {areaTreePanel.reload();}
}
