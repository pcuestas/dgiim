package app.gui.controller.user.tickets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.user.PurchaseTicketsPanel;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;
import app.theater.users.Client;

/**
 * Class ReserveTicketsAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class ReserveTicketsAction implements ActionListener {

    private AppWindow frame;
    private PurchaseTicketsPanel panel;
    private Performance perf;

	/**
	 * Constructor of ReserveTicketsAction
	 */
    public ReserveTicketsAction() {
        frame = AppWindow.getInstance();
        panel = frame.getPurchaseTicketsPanel();
    }

    /**
     * Reserve selected tickets
     * 
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        perf = MainController.getInstance().getCurrentPerformance();
        Client client = MainController.getInstance().getCurrentClient();
        int add = 0;
        int size;

        List<Ticket> remove = new ArrayList<>();
        List<Ticket> tickets = (List<Ticket>) panel.getElements();
        size = tickets.size();

        if (size == 0) {
            JOptionPane.showMessageDialog(panel, "Add tickets", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Ticket t : tickets) {
            if (!t.reserve(client)) {
                break;
            }
            remove.add(t);
            add++;
        }

        panel.removeFromList(remove);
        String s = "";
        if (add < size) {
            s += "No more tickets can be reserved";
        }

        JOptionPane.showMessageDialog(panel, "A total of " + add + " tickets were reserved. " + s);
        MainController.getInstance().getTicketsTreeSelection().valueChanged(null);
    }

}
