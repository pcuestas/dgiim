package pr3.traffic.vehicles;

/**
 * Enumeration of the possible pollution indexes
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 */
public enum PollutionIndex {
	A, B, C;
	
	private static final int DATEA = 2018;
	private static final int DATEB = 2010;
	
	/**
	 * @return the default polution index depending on the year
	 * @param year : the year of the vehicle
	 */
	public static PollutionIndex getPollutionIndex(int year) {
		if (year >= DATEA) return A;
		if (year >= DATEB) return B;
		return C;
	}
}
