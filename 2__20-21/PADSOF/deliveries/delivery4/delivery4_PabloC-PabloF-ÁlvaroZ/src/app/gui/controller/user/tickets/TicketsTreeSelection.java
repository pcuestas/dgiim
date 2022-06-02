package app.gui.controller.user.tickets;

import javax.swing.JTree;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.PurchaseTicketsPanel;
import app.theater.Application;
import app.theater.areas.*;
import app.theater.performances.Performance;

/**
 * Class TicketsTreeSelection
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TicketsTreeSelection implements TreeSelectionListener {
    private AppWindow frame;
    private DefaultMutableTreeNode lastNode;
    private JTree tree;

    PurchaseTicketsPanel ticketsPanel;

	/**
	 * Class TicketsTreeSelection
	 */
    public TicketsTreeSelection() {
        frame = AppWindow.getInstance();
        ticketsPanel = frame.getPurchaseTicketsPanel();
        tree = ticketsPanel.getTree();
    }

    /**
     * Modify the selection options depending on the selected area of the theater.
     * 
     * @param arg0 action event
     */
    @Override
    public void valueChanged(TreeSelectionEvent arg0) {

        Object selectedNode = tree.getLastSelectedPathComponent();
        lastNode = (DefaultMutableTreeNode) selectedNode;
        Performance perf = MainController.getInstance().getCurrentPerformance();

        if (lastNode != null) {
            Area a = Application.getInstance().getArea((String) (lastNode.getUserObject()));

            if (a instanceof SittingArea) {
                SittingArea area = (SittingArea) a;
                ticketsPanel.setPanel("sittingSelection");
                ticketsPanel.updateSitting(area.getRows(), area.getColumns(), perf.getNonAvailableIndexes(area));
            } else if (a instanceof StandingArea) {
                ticketsPanel.setPanel("standingSelection");
            } else {
                ticketsPanel.setSelectionVisibility(false);

            }

            ticketsPanel.clear();
        }
    }

    
    /** 
     * Get the selected area
     * @return Area the selected area
     */
    public Area getAreaSelected() {
        return Application.getInstance().getArea((String) (lastNode.getUserObject()));
    }
}
