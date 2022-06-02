package pr4.races.mains;

import java.io.IOException;

import pr4.races.*;
import pr4.races.exceptions.RaceException;

/**
 * Main for part 4
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class MainEx4 {

	public static void main(String[] args) {
		Race r;
		try {
			r = RaceReader.read(args[0]);
			r.allowAttacks(true);
			r.allowPowerUps(true);
			r.simulate();
		} catch (IOException e) {
			System.out.println("Error reading the file");
		} catch (RaceException e) {
			System.out.println(e);
		}
	}
}