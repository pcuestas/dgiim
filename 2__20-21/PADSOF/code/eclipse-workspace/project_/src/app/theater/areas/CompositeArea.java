package app.theater.areas;

import java.util.*;

import app.theater.Application;

/**
 * Class CompositeArea
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class CompositeArea extends Area{
	private static final long serialVersionUID = 331310196462351255L;

	private List<Area> areas = new ArrayList<>(); // areas inside 
	
	/**
	 * Constructor of composite area
	 * @param name name of the composite area
	 */
	public CompositeArea(String name) {
		super(name);
	}

	/**
	 * Capacity of the area
	 * @return capacity of the composite area
	 */
	@Override
	public int getCapacity() {
		int m = 0;
		for (Area a:areas) {
			m += a.getCapacity();
		}
		return m;
	}

	/**
	 * Add an area to the composite area
	 * @param a area to be added
	 * @return whether it has been added
	 */
	public boolean addArea(Area a) {
		if (Application.getInstance().areFuturePerformances()) {
			return false;
		}
		this.areas.add(a);
		return true;
	}

	/**
	 * Gets the simple areas of the area
	 * @return list with the areas
	 */
    @Override
    public List<SimpleArea> getSimpleAreas(){
		List <SimpleArea> list= new ArrayList<>();
        
        for (Area a: areas)
            list.addAll(a.getSimpleAreas());
        return list;
    }    
    

	/**
	 * set the annual pass price for all subareas
	 * @param price price
	 */
	@Override
	public void setAnnualPassPrice(double price) {
		for (Area a: this.areas) {
			a.setAnnualPassPrice(price);
		}
	}


	/**
	 * Removes an area (composite or simple)
	 * @param area the area to be removed
	 * @return false iff the argument area is not contained in this composite area  
	 */
    @Override
	public boolean removeArea(Area area) {
    	if (Application.getInstance().areFuturePerformances()) {
			return false;
		}
        if(areas.contains(area)){
			areas.remove(area);
            return true;
		}

        for(Area a:this.areas){
            if(a.removeArea(area)){
				return true;
			}
        }
        return false;	
	}

	/**
	 * Gets the child area with the name passed as parameter
	 * @param name Name of the area to be search
	 * @return the area with the same name iff it is contained in its children area.
	 */
	public Area getArea(String name){
		for(Area a: this.areas){
			if(a.getName().contentEquals(name))
				return a;
			Area area = a.getArea(name);
			if(area != null)
				return area;
		}

		return null;
	}

	/**
	 * Gets the subareas
	 * @return a list with the subAreas
	 */
	@Override
	public List<Area> getAreas(){
		return this.areas;
	}

	/**
	 * Returns an specific string of the area
	 * @return the string
	 */
	@Override
	public String toString(){
		return "Composite Area: "+getName()+". Capacity: "+getCapacity();
	}
}
