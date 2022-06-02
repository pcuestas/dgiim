package pr3.traffic.fines;

import java.io.*;

/**
 * FineWriter abstract class. Implements the writing of a list of fines in a
 * file, used to write the fines about ITVs expiration.
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 * 		   Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class FineWriter {
	/**
	 * Writes the fines in a file with the format: 
	 * NUMBER_PLATE;FINE_TYPE;POINTS 
	 * 
	 * @param fine fine to print in the file 
	 * @param file file where the fines are to be appended
	 */
	public static void write(Fine fine, String file) {
		try {
			BufferedWriter output = new BufferedWriter(
						       			new FileWriter(
						       				new File(file), true));// true to append at end of file
			
			output.write(fine.fileFormat()+"\n");
			
			output.close();
		} catch (IOException e) {
			System.out.println("There was an error.");
		}
	}
}
