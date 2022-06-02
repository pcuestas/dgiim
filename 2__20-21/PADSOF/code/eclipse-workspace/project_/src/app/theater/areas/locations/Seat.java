package app.theater.areas.locations;

import java.time.LocalDate;
import app.theater.areas.*;
import app.theater.util.*;

/**
 * Class Seat
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Seat extends Location{
	private static final long serialVersionUID = 5439629523869570995L;
	
	private int row ; // row of the seat
	private int column ; // coloumn of the seat
	private Interval disabled;
	
	/**
	 * Private constructor of seat
	 * @param a area of the seat
	 * @param row row of the seat
	 * @param column column of the seat
	 */
	private Seat(SittingArea a, int row, int column) {
        super(a); // constructor of Location 
        this.row = row;
        this.column = column;
    }

	
	/**
	 * Initialize seats in a sitting area
	 * @param a sitting area of the seat
	 * @return the matrix of seats
	 */
	public static Seat[][] initSeats(SittingArea a){
        int rows = a.getRows();
        int columns = a.getColumns();
		Seat[][] s = new Seat[rows][columns];
		for(int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
                s[i][j] = new Seat(a, i, j);
			}
		}
		return s;
	}
	
	/**
	 * Disable seat between two dates
	 * @param start : start date
	 * @param end : end date
	 */
	public void disable(LocalDate start, LocalDate end) {
		disabled = new Interval(start,end);
	}
	
	/**
	 * Check whether it is disabled in a certain date
	 * @param d date to check
	 * @return true iff it is disabled in date d
	 */
    @Override
	public boolean isDisabled(LocalDate d) {
		return (disabled==null) ? false :
				disabled.belongsToInterval(d);
	}

    /**
     * Representative string of the seat
     * @return string representing the seat
     */
	@Override
	public String toString(){
		return super.toString()+"(row:"+(1+row)+", col:"+(1+column)+")";
	}

	/**
	 * Getter for the seat's column
	 * @return seat's column
	 */
    public int getColumn() {
        return column;
    }

    /**
	 * Getter for the seat's row
	 * @return seat's row
	 */
    public int getRow() {
        return row;
    }

	/**
	 * Returns the interval in which the seat is disabled
	 * @return the interval or null if it's not disabled
	 */
	public Interval getInterval(){
		return disabled;
	}
}
