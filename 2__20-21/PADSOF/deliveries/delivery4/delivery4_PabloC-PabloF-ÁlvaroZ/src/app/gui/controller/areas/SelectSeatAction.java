package app.gui.controller.areas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import app.gui.controller.MainController;
import app.gui.view.AppWindow;
import app.gui.view.trees.AreaInfoPanel;
import app.gui.view.trees.AreaViewPanel;
import app.theater.areas.SittingArea;
import app.theater.areas.locations.Seat;
import app.theater.util.Interval;

/**
 * Class SelectSeatAction
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class SelectSeatAction implements ActionListener {
	
	private AreaInfoPanel areaInfoPanel;
	private Seat lastSeat;
	
	/**
	 * Constructor of SelectSeatAction
	 */
	public SelectSeatAction() {
		areaInfoPanel = AppWindow.getInstance().getAreaViewPanel().getAreaInfoPanel();
	}
	

	
    /** 
     * selects a seat for the admin panel (to disable seats)
     * @param e action event (selection of the seat)
     */
    @Override
	public void actionPerformed(ActionEvent e) {
		SittingArea area = (SittingArea) MainController.getInstance().getAreasTreeSelection().getAreaSelected();
		int row;
        int column;

		try {
			row=Integer.parseInt((areaInfoPanel.getRowText()));
			column=Integer.parseInt((areaInfoPanel.getColumnText()));
		}catch(NumberFormatException x) {
			JOptionPane.showMessageDialog(areaInfoPanel, "Invalid number.");
			return;
		}
		
		if(row> area.getRows() || column>area.getColumns() || row<1 ||column<1) {
			JOptionPane.showMessageDialog(areaInfoPanel, "Seat not existing. Area with "
					+ area.getRows()+" rows and "+area.getColumns()+" columns.");
			return;
		}
		
		
		lastSeat=area.getSeat(row-1, column-1);
		Interval inter=lastSeat.getInterval();
		
		if(inter!=null) {
			areaInfoPanel.update(null, null, inter.getBeginning(), inter.getEnd());
		}else {
			areaInfoPanel.update(null, null, "", "", false);
		}
		
		JOptionPane.showMessageDialog(areaInfoPanel, "Seat selected.");
	}
	
	
    /** 
	 * Gets the last selected seat
     * @return the last seat selected
     */
    public Seat getSeat() {
		return lastSeat;
	}

}
