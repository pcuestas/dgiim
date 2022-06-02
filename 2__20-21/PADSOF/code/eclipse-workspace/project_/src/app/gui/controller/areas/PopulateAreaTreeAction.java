package app.gui.controller.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import app.gui.view.trees.*;
import app.theater.Application;
import app.theater.areas.Area;
import app.gui.view.*;

/**
 * Class PopulateAreaTreeAction. Fills the area's tree when the app is loaded.
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class PopulateAreaTreeAction {
    
	private static AppWindow frame;
	private static JTree tree;
	private static Application app;
	private static DefaultTreeModel dataModel;
	
	/**
	 * Constructor of PopulateAreaTreeAction
	 */
	public PopulateAreaTreeAction() {
		frame=AppWindow.getInstance();
		tree=frame.getAreaViewPanel().getAreaTreePanel().getTree();
		app=Application.getInstance();
		dataModel=frame.getAreaViewPanel().getAreaTreePanel().getDataModel();
	}
    /**
     * populates the tree of areas
     */
	public void populateTree() {
		DefaultMutableTreeNode root=frame.getAreaViewPanel().getAreaTreePanel().getRoot();
		root.removeAllChildren();
		populateNode(root);
		frame.getAreaViewPanel().reload();
	}
	
	
    /** 
     * populates a node recursively
     * @param parentNode the parent node to be populated
     */
    private static void populateNode(DefaultMutableTreeNode parentNode) {
		Area a=Application.getInstance().getArea((String)parentNode.getUserObject());
		for(Area area: a.getAreas()) {
			DefaultMutableTreeNode inserNode=new DefaultMutableTreeNode(area.getName());
         	dataModel.insertNodeInto(inserNode,parentNode,0);
         	populateNode(inserNode);
		}
	}

	
}
