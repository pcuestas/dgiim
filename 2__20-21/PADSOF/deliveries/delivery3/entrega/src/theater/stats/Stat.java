package app.theater.stats;


import java.util.*;

import app.theater.areas.Area;
import app.theater.events.Event;
import app.theater.performances.Performance;

/**
 * Class Stat
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Stat {
    private Event event;
    private Performance performance;
    private Area area;
	private double revenue; //does not take into account the tickets purchased with passes
    private int purchasedWithPass; // number of tickets purchased with pass
    private int attendance; // sum of the purchased tickets
    private int totalTickets; //sum of the sold and unsold tickets

    /**
     * Constructor of stat
     * @param event event of the stat
     * @param performance performance of the stat
     * @param area area of the stat
     * @param revenue revenue stat
     * @param attendance attendance stat
     * @param purchasedWithPass tickets sold with pass stat
     * @param totalTickets total tickets
     */
    public Stat(Event event, Performance performance, Area area, double revenue, int attendance, int purchasedWithPass, int totalTickets){
        this.totalTickets = totalTickets;
        this.event = event; 
        this.performance = performance;
        this.area = area;
        this.revenue = revenue;
        this.attendance = attendance;
        this.purchasedWithPass = purchasedWithPass;
    }

    /**
     * Return a string representing the stat
     * @return string representing the stat
     */
    @Override
    public String toString(){
        String ret = "";
        if(event!=null){
            ret += event.getTitle();
        }
        if(performance!=null){
            ret += " "+performance.getDate();
        }
        if(area!=null){
            ret += " Area: "+ area.getName();
        }
        ret += " revenue: "+revenue 
               + ", attendance: "+String.format(Locale.ROOT,"%.2f",getAttendancePercentage())+"%";
        return ret;
    }

    /**
     * Gets the percentage of attendance
     * @return attendance percentage
     */
    public double getAttendancePercentage(){ 
        return (100*(double)attendance/(double)totalTickets);
    }

    /**
     * Gets the total tickets
     * @return total tickets 
     */
    public int getTotalTickets(){return totalTickets;}
    
    /**
     * Gets the total attendance
     * @return attendance
     */
    public int getAttendance(){return attendance;}
    
    /**
     * Gets the total revenue
     * @return revenue
     */
    public double getRevenue(){return revenue;}

    /**
     * Gets the purchased with pass stat
     * @return number of tickets purchased with a pass
     */
	public int getPurchasedWithPass() {
		return this.purchasedWithPass;
	}

    /**
     * Gets the event of the stat
     * @return the event
     */
    public Event getEvent(){
        return this.event;
    }

    /**
     * Gets the performance of the stat
     * @return the performance
     */
    public Performance getPerformance(){
        return this.performance;
    }

    /**
     * Gets the area of the stat
     * @return the area
     */
    public Area getArea(){
        return this.area;
    }

    /**
     * Sort the stats
     * @param stats list of stats to sort
     * @param condition criteria to sort
     * @param reversed decides if the list is reversed or not
     */
	public static void sortStats(List<Stat> stats, StatComparator condition, boolean reversed){
        stats.sort(condition);
        if(reversed)
            Collections.reverse(stats);
    }

    /**
     * Show the stats
     * @param stats list of stats to show
     * @return string with the printed stats 
     */
	public static String show(List<Stat> stats) {
		String ret = "";
        for (Stat s: stats){
            ret += s+"\n";
        }
        return ret;
	}
}
