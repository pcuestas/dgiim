package app.theater.util;

import app.theater.performances.Performance;
import app.theater.performances.tickets.*;
import app.theater.areas.*;
import app.theater.areas.locations.*;

import java.util.*;

/**
 * Class Point
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Point {
	private int row = 0;
	private int column = 0;

    /**
     * Constructor of point
     * @param row row
     * @param column column
     */
    public Point(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Empty constructor: initializes values to 0
     */
    public Point(){}
	
	/**
	 * Calculates the center of mass of a sittingArea based on the purchased tickets
	 * @param purchasedTickets list of purchased tickets
	 */
	private static Point barycenter(List<Ticket> purchasedTickets) {
        Point barycenter = new Point();

        if(purchasedTickets.isEmpty()){
            return barycenter;
        }
        
        int numPoints = 0;
		
		for(Ticket t: purchasedTickets) {
			Seat seat=(Seat)(t.getLocation());
			int r = seat.getRow();
			int c = seat.getColumn();
			barycenter.row += r;
			barycenter.column += c;
			numPoints++;
		}

		barycenter.row = (int) Math.round(((double)barycenter.row) / numPoints);
		barycenter.column = (int) Math.round(((double)barycenter.column) / numPoints);

        return barycenter;
	}
	
    /**
     * The furthest point from the center of mass of the purcahsed 
     * tickets of the performance in this sitting area
     * @param performance performance
     * @param area sitting area
     * @return the point that is furthest from the most purchased seats of 
     * the performance in a sitting area
     */
    public static Point getFurthestPoint(Performance performance, SittingArea area){
        Point b = barycenter(performance.getPurchasedTickets(area));
        Point p = new Point();

        if(b.row > area.getRows()/2){
            p.row = 0;
        }else{
            p.row = area.getRows()-1;
        }

        if(b.column > area.getColumns()/2){
            p.column = 0;
        }else{
            p.column = area.getColumns()-1;
        }

        return p;
    }
	 
	/**
     * Gets the row of the point
     * @return the row
     */
	public int getRow() {return row;}
	
    /**
     * Gets the column of the point
     * @return the row
     */
	public int getColumn() {return column;}
}

