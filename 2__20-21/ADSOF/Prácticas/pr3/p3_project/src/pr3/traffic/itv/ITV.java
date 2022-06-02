package pr3.traffic.itv;
import java.time.LocalDate;

/**
 * Class ITV
 * @author Pablo Cuesta: pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 */
public class ITV {
	private LocalDate date; //date of inspection
	private Garage garage;//garage of inspection
	private String comments ; //comments on the ITV
	
	/**
	 * Constructor of ITV
	 * @param date date of inspection
	 * @param garage garage
	 * @param comments comments on the ITV
	 */
	public ITV(LocalDate date, Garage garage, String comments) {
		this.date = date;
		this.garage = garage;
		this.comments = comments;
	}

	/**
	 * Date of itv
	 * @return the date of the itv
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * The garage where the itv was made
	 * @return the garage
	 */
	public Garage getGarage() {
		return garage;
	}

	/**
	 * toString method
	 */
	@Override
	public String toString() {
		return "ITV [date=" + date + ", garage=" + garage.getName() + ", comments=" + comments + "]";
	}
	
	
}
