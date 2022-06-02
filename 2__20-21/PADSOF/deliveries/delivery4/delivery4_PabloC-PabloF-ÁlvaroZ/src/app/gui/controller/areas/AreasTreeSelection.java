package app.gui.controller.areas;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.trees.AreaInfoPanel;
import app.gui.view.trees.AreaTreePanel;
import app.theater.Application;
import app.theater.areas.Area;
import app.theater.areas.CompositeArea;
import app.theater.areas.SimpleArea;
import app.theater.areas.SittingArea;

/**
 * Class AreasTreeSelection
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AreasTreeSelection implements TreeSelectionListener {
	private AppWindow frame;
	private DefaultMutableTreeNode lastNode;
	private AreaTreePanel areaTreePanel;
	private JTree tree;
	
	/**
	 * Constructor of AreasTreeSelection
	 */
	public AreasTreeSelection() {
		frame=AppWindow.getInstance();
		areaTreePanel=frame.getAreaViewPanel().getAreaTreePanel();
		tree=areaTreePanel.getTree();
		lastNode=areaTreePanel.getRoot();
	}
	
	
    /** 
	 * update the specific panels for each type of area int he area view panel
     * @param arg0 tree selection event received
     */
    @Override
	public void valueChanged(TreeSelectionEvent arg0) {
		AreaInfoPanel panel=frame.getAreaViewPanel().getAreaInfoPanel();
		
		Object selectedNode = tree.getLastSelectedPathComponent();
		lastNode=(DefaultMutableTreeNode)selectedNode;
		
		if(lastNode!=null) {
			Area a= Application.getInstance().getArea((String)(lastNode.getUserObject()));		
			panel.update(a.toString(), null, null, null);
			
			if(a instanceof SittingArea) {
				panel.setSeatPanelVisible(true);
			}else {
				panel.setSeatPanelVisible(false);
			}
			
			if(a instanceof CompositeArea) {
				panel.setSimpleAreaPanelVisible(false);
			}else {
				panel.setSimpleAreaPanelVisible(true);
				panel.update(null, Application.getInstance().getAnnualPassPrice((SimpleArea)a), null, null);
			}
		}	
		
	}
	
	
    /** 
     * set the last node
     * @param node new last node
     */
    public void setLastNode(DefaultMutableTreeNode node) {
		lastNode = node;
	}
	
	
    /** 
     * gets the selected area
     * @return the selected area
     */
    public Area getAreaSelected() {
		return Application.getInstance().getArea((String)(lastNode.getUserObject()));		
	}

}
