package pr3.traffic.fines;
import java.util.List;

/**
 * Test the RineReader class
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 */
public class FineTester {
	/**
	 * Entry point of the tester
	 * @param args not used
	 */
	public static void main(String[] args) {
		List<Fine> fines = FineReader.read("txt/fines_radar1.txt");
		
		for (Fine m : fines) 
			System.out.println(m+"\n------------");		
		
	}
	
}
