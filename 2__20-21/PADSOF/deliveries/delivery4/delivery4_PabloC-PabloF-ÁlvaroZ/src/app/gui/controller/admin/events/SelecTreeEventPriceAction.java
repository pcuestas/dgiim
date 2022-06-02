package app.gui.controller.admin.events;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.things.EventPricesPanel;
import app.theater.Application;
import app.theater.areas.Area;
import app.theater.areas.CompositeArea;
import app.theater.areas.SimpleArea;

/**
 * Class SelecTreeEventPriceAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SelecTreeEventPriceAction implements TreeSelectionListener {
	private AppWindow frame;
	private DefaultMutableTreeNode lastNode;
	private EventPricesPanel panel;
	private JTree tree;
	private int nPanel;
	
	/**
	 * Constructor of SelecTreeEventPriceAction
	 * @param nPanel iff 1 the panel is event prices panel. Else, establish cycle panel.
	 */
	public SelecTreeEventPriceAction(int nPanel) {
		this.nPanel=nPanel;
		frame=AppWindow.getInstance();
		if(nPanel==1) {
			panel=frame.getEventPricesPanel();
		}else {
			panel=frame.getEstablishCyclePricePanel();
		}
		
		tree=panel.getTree();
	}
	
	
    /** 
     * modifies the label of the event prices panel when an area is selected
     * @param arg0 tree selection event received
     */
    @Override
	public void valueChanged(TreeSelectionEvent arg0) {
		Object selectedNode = tree.getLastSelectedPathComponent();
		lastNode=(DefaultMutableTreeNode)selectedNode;
		Area a;
		try {
	        a = Application.getInstance().getArea((String)(lastNode.getUserObject()));			
		}catch (Exception e ){
			return ;
		}        
		
		if(lastNode!=null) {
			Double value=0.0;
			a = Application.getInstance().getArea((String)(lastNode.getUserObject()));		
			
			if(a instanceof CompositeArea ) {
				panel.setVisibilitySimplePanel(false);
			}else {
				panel.setVisibilitySimplePanel(true);
				if(nPanel==1) {
					value=MainController.getInstance().getCurrentEvent().getPrice((SimpleArea)a);
				}else {
					value=MainController.getInstance().getCreateCycleAction().getMap().
							get(a);
				}
				
				panel.update(value);
            }
		}		
	}
	
	
    /** 
     * setter for the last node
     * @param node the new last node
     */
    public void setLastNode(DefaultMutableTreeNode node) {
		lastNode = node;
	}
	
	
    /** 
     * the selected area
     * @return Area the selected area
     */
    public Area getAreaSelected() {
		return Application.getInstance().getArea((String)(lastNode.getUserObject()));		
	}

}


