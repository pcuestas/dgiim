package app.gui.controller.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.trees.*;
import app.theater.Application;
import app.theater.areas.Area;
import app.theater.areas.SimpleArea;

/**
 * Class RemoveAreaAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class RemoveAreaAction implements ActionListener{
	private AppWindow frame;
	private AreaViewPanel areasViewPanel;
	private JTree tree;

	/**
	 * Constructor of RemoveAreaAction
	 */
	public RemoveAreaAction() {
		frame = AppWindow.getInstance();
		areasViewPanel = frame.getAreaViewPanel();
		tree = areasViewPanel.getAreaTreePanel().getTree();

	}

	/**
	 * Removes an area when an action is received
	 * @param e action received
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MainController controller = MainController.getInstance();
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		String name = (String) selectedNode.getUserObject();
		Area area = Application.getInstance().getArea(name);
		
		//areasViewPanel.getAreaTreePanel().getDataModel().reload(areasViewPanel.getAreaTreePanel().getRoot());
		
		if (!(Application.getInstance().removeArea(area))) {
			JOptionPane.showMessageDialog(areasViewPanel, "The area could not be removed");
		}else {
			areasViewPanel.getAreaTreePanel().getDataModel().removeNodeFromParent(selectedNode);
			controller.getAreasTreeSelection().setLastNode(areasViewPanel.getAreaTreePanel().getRoot());
		}
	}
}
