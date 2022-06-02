package app.theater.util;

import java.time.*;
import java.io.*;

/**
 * Class Interval
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Interval implements Serializable{
	private static final long serialVersionUID = 677103360484704085L;
	
	private LocalDate start;
	private LocalDate end;  // end date of being disabled
	
	/**
	 * Constructor of interval
	 * @param start start date
	 * @param end end date
	 */
	public Interval(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Does date d belong to this interval
	 * @param d date to compare
	 * @return true iff date d belongs to this interval
	 */
	public boolean belongsToInterval(LocalDate d) {
		return d.compareTo(start)>=0 && d.compareTo(end)<=0;
	}

	/**
	 * 
	 * @return begining of the interval 
	 */
	public String getBeginning(){
		return start.toString();
	}

	/**
	 * 
	 * @return end of the interval
	 */
	public String getEnd(){
		return end.toString();
	}
}
