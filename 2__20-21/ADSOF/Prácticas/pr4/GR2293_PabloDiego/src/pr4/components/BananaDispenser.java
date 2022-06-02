package pr4.components;

import pr4.vehicles.*;

/**
 * Component Banana dispenser
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class BananaDispenser extends Component {

	/**
	 * Constructor of the component
	 * @param vehicle where the component belongs
	 */
	public BananaDispenser(IVehicle vehicle) {
		super(vehicle);
	}

	/**
	 * The name of the component
	 * @return name of the component
	 */
	@Override
	public String getName() {
		return "Banana dispenser";
	}

	/**
	 * Number of turns it costs to repair
	 * @return Number of turns it costs to repair
	 */
	@Override
	public int costRepair() {
		return 4;
	}



}
