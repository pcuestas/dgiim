package app.gui.view.areas;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import app.gui.view.Util;




/**
 * Class SeatPanel. Panel that displays the seats of a sittingArea using a matrix of checkBoxes
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class SeatPanel extends JPanel{
    private JScrollPane scroll;
	private JPanel checkPanel = new JPanel();
	private JButton button = new JButton ("Add tickets to selection");
	
	JCheckBox[][] checkBoxes;   
	
	/**
	* public constructor
	 */
	public SeatPanel() {
		
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		scroll = new JScrollPane(checkPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
		this.add(scroll);
		button.setAlignmentX(CENTER_ALIGNMENT);
		this.add(button);
	}
    

	/**
	* updates the matrix of checkBoxes
	* @param rows rows of the matrix
	* @param columns columns of the matrix
	* @param nonAvailable indexes of checkBoxes that cannot be checked 
	*/
    public void update(int rows, int columns, List<Util.SittingPair> nonAvailable){
        checkBoxes = new JCheckBox[rows][columns];
        checkPanel.removeAll();
		checkPanel.setLayout(new GridLayout(rows,columns));
        for(int r=0; r<rows; r++) {
            for(int c=0;c<columns;c++) {
                checkBoxes[r][c] = new JCheckBox(r + "-" + c);
                
				
				if(nonAvailable.contains(new Util.SittingPair(r,c))) {
                    checkBoxes[r][c].setEnabled(false);
				}
                checkPanel.add(checkBoxes[r][c]);
			}
		}
        this.validate();
    }
	
	/**
	*
	* @return list of indexes of the selected checkBoxes
	 */
	public List<Util.SittingPair> getIndexes(){
        List <Util.SittingPair> indexes = new ArrayList<>();
		int r = 0;
		
		for(JCheckBox[] row: checkBoxes){
            int c = 0;
			for(JCheckBox check: row){
				if(check.isSelected()){
					indexes.add(new Util.SittingPair(r,c));
				}
				c++;
			}
			r++;
		}

		return indexes;
	}
	
	/**
	* @param act action performed when button AddTickets is selected
	 */
	public void setController(ActionListener act) {
		button.addActionListener(act);
	}
}