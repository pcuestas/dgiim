package app.gui.controller.areas;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.SetPanelAction;
import app.gui.view.AppWindow;
import app.theater.Application;
import app.theater.areas.Area;
import app.theater.areas.SimpleArea;

/**
 * Class AddAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class AddAreaAction extends SetPanelAction{
	protected AppWindow frame;
    private JTree tree;
    private DefaultMutableTreeNode lastNode;
    
    /**
     * Constructor of AddAreaAction
     */
	public AddAreaAction() {
		super("createAreaPanel");
		frame = AppWindow.getInstance();
        tree=frame.getAreaViewPanel().getAreaTreePanel().getTree();
        lastNode=frame.getAreaViewPanel().getAreaTreePanel().getRoot();
	}
	
	
    /** 
     * adds an area to the theater, if it is possible
     * @param e action received
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultMutableTreeNode aux=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        
        if(aux!=null) {
        	lastNode=aux;
        }
        
		Area parentArea = Application.getInstance().getArea((String)(lastNode.getUserObject()));

        if(parentArea instanceof SimpleArea) {
        	JOptionPane.showMessageDialog(null, "Area could not be added. Can't add areas to a simple area.", 
        						"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(Application.getInstance().areFuturePerformances()) {
        	JOptionPane.showMessageDialog(null, "Area could not be added. There are future performances.", 
					"Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
      
        super.actionPerformed(e);
	}
}
