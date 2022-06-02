package app.gui.view.tables;

import java.util.Vector;

import javax.swing.table.*;

/**
 * Class TabkeModelNOnEditable. Class that extends DefaultTableModel. Cells are not editable
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
@SuppressWarnings("serial")
public class TableModelNonEditable extends DefaultTableModel {
	
	/**
	* public constrcutor
	* @param data data of the table
	* @param columnNames names of the columns
	 */
	public TableModelNonEditable(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	/**
	* all the cells are non editable
	* @return false by default
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
