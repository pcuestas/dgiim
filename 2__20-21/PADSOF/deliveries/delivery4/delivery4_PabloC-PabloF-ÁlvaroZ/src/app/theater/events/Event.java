package app.theater.events;

import java.util.*;
import java.time.*;
import java.io.*;

import app.gui.view.things.IDisplayable;
import app.theater.*;
import app.theater.searches.SearchBy;
import app.theater.stats.Stat;
import app.theater.areas.*;
import app.theater.performances.Performance;
import app.theater.util.*;

/**
 * Abstract Class Event
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public abstract class Event implements Serializable, IDisplayable{
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 7129037966024037166L;
	
	private String title ;
	private String description;
	private int durationMin;
	private String author;
	private String director;
	private String picture;
	private double restriction = 0; // percentage of restricted tickets
	private Map<SimpleArea,Double> prices;
	private final List<Performance> performances = new ArrayList<>();
	
	/**
	 * Constructor of Event
	 * @param title       	title of the event
	 * @param description 	description of the event
	 * @param durationMin	duration (in minutes) of the event
	 * @param author		author of the event
	 * @param director		director of the event
     * @param picture       picture of the event
	 */
	public Event(String title, String description, int durationMin, 
			     String author, String director, String picture) {
		this.title = title;
		this.description = description;
		this.durationMin = durationMin;
		this.author = author;
		this.director = director;
		this.picture=picture;
	}
	
	/**
	 * Add a performance of the event
	 * @param date	date of the new performance
	 * @return true if prices are set so performance can be added; false if not
	 */
	public boolean addPerformance(LocalDate date) {
        if(!this.checkPrices() || !Application.getInstance().canAddNewPerformance(date)){
            return false;		    
        }
        performances.add(new Performance(this, date)) ;
        return true;
	}

	/**
	 * Removes a performance of the event
	 * @param performance	performance to be removed
	 */
    public void removePerformance(Performance performance) {
        performances.remove(performance);
	}

	/**
     * Returns the performance
	 * @param happened		boolean to decide whether to show performances that already happened or not
	 * @return List of performances containing the performances of the event happened or not depending on the parameter
	 */
	public List<Performance> getPerformances(boolean happened) {
        List<Performance> list = new ArrayList<>();

        if(!happened){
            for(Performance p: performances){
                if(!p.hasHappened()){
                    list.add(p);
                }
            }
        }else{
            for(Performance p: performances){
                if(p.hasHappened()){
                    list.add(p);
                }
            }
        }
        return list;
	}
	
    /**
     * Gets the list of performances
     * @return list (unmodifiable) with the performance
     */
	public List<Performance> getPerformances(){
		return Collections.unmodifiableList(this.performances);
	}


    /**
     * Gets list of performances in a given interval
     * @return list with the performances
     */
    public List<Performance> getPerformances(LocalDate d1, LocalDate d2){
        List <Performance> list = new ArrayList<>();
        Interval interval = new Interval(d1,d2);

        for(Performance perf: performances){
            if(interval.belongsToInterval(perf.getDate()))
                list.add(perf);
        }

        return list;
    }

	/**
	 * Returns director/author/title of the event depending on the type of search
	 * @param by type of search 
	 * @return director/author/title of the event
	 */
	public String getATR(SearchBy by){
		switch(by){
			case DIRECTOR:
                return this.director;
            case AUTHOR:
				return this.author;
			case TITLE:
				return this.title;
        }
		return "";
	}
    
    /** GETTERS AND SETTERS */
	/**
	 * Getter of title
	 * @return String containing the title of the event
	 */
    public String getTitle(){
		return this.title;
	}
    
	/**
	 * Sets restrictions for an event
     * @param restriction	restriction to set
	 * @return true if the restriction was set correctly; false if not
	 */
	public boolean setRestriction(double restriction) {
        //Check that there are no planned performances
		if(!this.getPerformances(false).isEmpty() || !Util.isPercentage(restriction)) 
			return false;
            
		this.restriction=restriction; 
		return true;
	}
	
	/**
	 * Getter of restriction
     * @return double with the restriction of the event
	 */
    public double getRestriction(){
		return restriction; 
    }

    /**
     * Set the prices for the performance
     * @param prices a map of prices with all of the simple areas assignd to a price
     */
    public void setPrices(Map<SimpleArea,Double> prices){
        this.prices = prices;
    }

    /**
     * Get the price in an area
     * @param area area to get the price from
     * @return -1 if there is no price assigned. The price otherwise
     */
	public double getPrice(SimpleArea area){
        if(prices == null || !prices.containsKey(area)){
            return -1;
        }


		return this.prices.get(area);
	}

	/**
	 * checks if all the areas have a price assignated for the event
	 * @return true iff all areas have a price assignated
	 */
    public boolean checkPrices(){
        if(this.prices == null){
            return false;
        }
        for(SimpleArea a:Application.getInstance().getSimpleAreas()){
            if(!this.prices.containsKey(a)){
                return false;
            }
        }
        return true;
    }

    /**
     * adds a price to an area
     * @param a area to add a price to
     * @param price price to be added to the area
     * @return true iff it was added correctly
     */
    public boolean addPrice(SimpleArea a, double price){
        if (this.prices == null){
            this.prices = new HashMap<>();
        }
        if(!getPerformances(false).isEmpty()){
            return false;
        }
        this.prices.put(a,price);
        return true;
    }
    
    /**
     * Get the location of the picture of the performance
     * @return the location of the picture of the performance
     */
	public String getPicture(){
        return this.picture;
	}
	

    
    /*************Stats******** */

	/**
	 * Returns the general stats of this event
     * @return the stat of the event
	 */
    public Stat getStats() {
        int attendance=0;
        int totalTickets=0;
        double revenue=0;
        int withPass=0;
    
        for(Performance p: this.performances){
            Stat performanceStat = p.getStats();
            attendance += performanceStat.getAttendance();
            totalTickets += performanceStat.getTotalTickets();
            withPass += performanceStat.getPurchasedWithPass();
            revenue += performanceStat.getRevenue();
        }
    
        return (new Stat(this, null, null, revenue, attendance, withPass, totalTickets));
    }

	/**
     * The stat of an area for this event (for every perfromance)
     * @param area area to get the stats from
     * @return the stats of the area area in this event
     */
	public Stat getStats(Area area){
        int attendance = 0;
        double revenue = 0;
        int totalTickets = 0;
        int withPass = 0;
    
        for(Performance p: this.performances){
            Stat performanceStat = p.getStats(area);
            attendance += performanceStat.getAttendance();
            withPass += performanceStat.getPurchasedWithPass();
            revenue += performanceStat.getRevenue();
            totalTickets += performanceStat.getTotalTickets();
        }
    
        return (new Stat(this, null, area, revenue, attendance, withPass, totalTickets));
    }

    /**
     * List of stats of each performance of the event
     * @return the list of stats of every performance
     */
    public List<Stat> getStatsPerformances(){
        List<Stat> list = new ArrayList<>();
        
        for(Performance p: this.performances){
            list.add(p.getStats());
        }
        
        return list;
    }

    /**
     * List of stats of each area for this event
     * @return the list of stats of each area
     */
    public List<Stat> getStatsAreas(){
        List<Stat> list = new ArrayList<>();
        
        for(SimpleArea a: Application.getInstance().getSimpleAreas()){
            list.add(this.getStats(a));
        }
        return list;
    }

    /**
     * toString method with the info of the event
     * @return the string with the event info
     */
	@Override
	public String toString() {
		return this.title;
	}    

    /**
     * The duration 
     * @return the duration (minutes)
     */
    public int getDuration(){
        return this.durationMin;
    }

    /**
     * Author 
     * @return the author
     */
    public String getAuthor(){
        return this.author;
    }

    /**
     * Description 
     * @return the description of the event
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Director 
     * @return the director of the event
     */
    public String getDirector(){
        return this.director;
    }

    /**
     * Gets the information of the event
     * @return the information
     */
    public String getInfo(){
        return "<html>"
            + "Title: " + title + "<br>"
            + "Duration: " + durationMin + "<br>"     
            + "Author: " + author  + "<br>"
            + "Director: " + director + "<br>"
            + description
            +"</html>";
    }
}