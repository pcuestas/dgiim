package pr4.components;

import pr4.vehicles.*;

/**
 * A critical component. (Wheels or engine)
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class CriticalComponent extends Component {
	private String name; // wheels or engine
	
	/**
	 * Constructor of the component
	 * @param vehicle where the component belongs
	 * @param name name of the component: "Wheels" or "Engine"
	 */
	public CriticalComponent(IVehicle vehicle, String name) {
		super(vehicle);
		this.name = name;
	}
	
	/**
	 * The name of the component
	 * @return name of the component
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Number of turns it costs to repair
	 * @return Number of turns it costs to repair
	 */
	@Override
	public int costRepair() {
		return 3;
	}

	/**
	 * Whether it is critical
	 * @return Whether it is critical
	 */
	@Override
	public boolean isCritical() {
		return true;
	}



}
