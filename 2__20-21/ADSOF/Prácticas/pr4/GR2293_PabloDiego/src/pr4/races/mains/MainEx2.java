package pr4.races.mains;

import java.io.*;

import pr4.races.*;
import pr4.races.exceptions.*;

/**
 * Main for the part 2
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class MainEx2 {

	public static void main(String[] args) {
		Race r;
		try {
			r = RaceReader.read(args[0]);
			r.simulate();
		} catch (IOException e) {
			System.out.println("Error reading the file");
		} catch (RaceException e) {
			System.out.println(e);
		}
	}

}
