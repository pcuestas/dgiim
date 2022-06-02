package pr4.components;

import pr4.vehicles.*;

/**
 * Window component
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class Window extends Component {

	/**
	 * Constructor of window
	 * @param vehicle where the window belongs
	 */
	public Window(IVehicle vehicle) {
		super(vehicle);
	}

	/**
	 * The name of the component
	 * @return name of the component
	 */
	@Override
	public String getName() {
		return "Window";
	}

	/**
	 * Number of turns it costs to repair
	 * @return Number of turns it costs to repair
	 */
	@Override
	public int costRepair() {
		return 2;
	}

}
