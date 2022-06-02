package pr4.races.mains;

import java.io.*;

import pr4.races.Race;
import pr4.races.RaceReader;

/**
 * Main for part 1
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class MainEx1 {

	public static void main(String[] args) {
		Race r;
		try {
			r = RaceReader.read(args[0]);
			System.out.println(r);
		} catch (IOException e) {
			System.out.println("Error reading the file");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
