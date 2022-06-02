package app.gui.controller.admin.areas;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.gui.view.admin.*;
import app.gui.view.areas.CreateAreaPanel;
import app.theater.Application;
import app.theater.areas.Area;
import app.theater.areas.CompositeArea;
import app.theater.areas.SimpleArea;
import app.gui.view.trees.*;
import app.gui.controller.*;


/**
 * Class CreateAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public abstract class CreateAreaAction extends SetPanelAction {
    protected AppWindow frame;
	protected AreaViewPanel areaViewPanel;
    protected CreateAreaPanel createAreaPanel;
    private JTree tree;

    /**
     * Constructor of CreateAreaAction
     */
    public CreateAreaAction() {
		super("areaViewPanel");
		frame = AppWindow.getInstance();
		areaViewPanel= frame.getAreaViewPanel();
        createAreaPanel = frame.getCreateAreaPanel();
        tree=frame.getAreaViewPanel().getAreaTreePanel().getTree();
	}
	
    /**
     * Decides what to do when an action event is received
     * @param ev action received
     */
    @Override
    public void actionPerformed(ActionEvent ev){       	
        Area a = this.createArea();
        DefaultMutableTreeNode lastNode;
        Area parentArea ;
        try {
        	lastNode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        	parentArea = Application.getInstance().getArea((String)(lastNode.getUserObject()));
        }catch(Exception exc) {
            JOptionPane.showMessageDialog(createAreaPanel, "You had to select the parent area",
    				"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (a == null) {
            JOptionPane.showMessageDialog(null, "Area could not be added. Parameters were incorrect or name repeated.");
            return;
        }
        
        if(((CompositeArea)(parentArea)).addArea(a)) {
        	areaViewPanel.getAreaTreePanel().getDataModel().
         	insertNodeInto(new DefaultMutableTreeNode(a.getName()),lastNode,0);
        }else {
             JOptionPane.showMessageDialog(createAreaPanel, "Area could not be added. There are future performances",
     				"Error", JOptionPane.ERROR_MESSAGE);
        }

		MainController.getInstance().setCurrentArea(a);
        // update the modify area panel
		super.actionPerformed(ev);
   }

    /**
     * Creates a new area
     * @return a new composite area iff the area was created or null
     */
    public Area createArea() {
	   Area a = Application.getInstance().getArea(createAreaPanel.getAreaName());
   		if(a!=null) { //There is already an area with the same name
   			return null;
   		}
   		
   		return new CompositeArea("");
   }
}