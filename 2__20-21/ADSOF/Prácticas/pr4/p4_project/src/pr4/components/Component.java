package pr4.components;

import pr4.vehicles.*;

/**
 * Class Component
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class Component implements IComponent{
	private IVehicle vehicle;
	private boolean damage = false; // damage of the component
	private int turnsRepaired = 0;// # of turns it has been repaired
	
	/**
	 * Constructor of the component
	 * @param vehicle where the component belongs
	 */
	protected Component(IVehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	/**
	 * Whether it is damaged
	 * @return Whether it is damaged
	 */
	@Override
	public boolean isDamaged() {
		return damage;
	}
	
	/**
	 * Set the damage
	 * @param damage new damage
	 */
	@Override
	public void setDamaged(boolean damage) {
		this.turnsRepaired = 0;
		this.damage = damage;
	}
	
	/**
	 * Its vehicle
	 * @return its vehicle
	 */
	@Override
	public IVehicle getVehicle() {
		return this.vehicle;
	}
	
	/**
	 * One turn of repairing this component, if it is damaged
	 */
	@Override
	public void repair() {
		if(damage) {
			this.turnsRepaired += 1;
			System.out.println(this.vehicle.getName() + " " 
			                   + this.getName() + " is being repaired "
			                   + this.turnsRepaired 
			                   + "/" + this.costRepair());
			if(this.turnsRepaired == this.costRepair()) {//already repaired
				this.setDamaged(false);
			}
		}
	}
	
	/**
	 * Info of the component
	 * @return the info of the component
	 */
	@Override
	public String toString() {
		return this.getName() + ". Is damaged: " + this.isDamaged()
		       + ". Is critical: " + this.isCritical();
	}
	
	/**
	 * Whether it is critical
	 * @return Whether it is critical
	 */
	@Override
	public boolean isCritical() {
		return false; // default
	}
	
}
