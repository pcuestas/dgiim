package pr3.traffic.fines;
import java.util.*;
import java.io.*;

/**
 * FineReader abstract class. Implements the reading of a file and returns the
 * list of fines in it
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public abstract class FineReader {
	/**
	 * Reads the fines in a file with the format:
	 * 		NUMBER_PLATE;FINE_TYPE;POINTS
	 * and returns the list of fines in said file.
	 * @param file file where the fines are
	 * @return the list of fines
	 */
	public static List<Fine> read(String file){
		try {
			List<Fine> l = new ArrayList<>();
			BufferedReader buffer = new BufferedReader(
											new InputStreamReader(
													new FileInputStream(file)));
			
			String line ;
			
			while((line=buffer.readLine())!=null) {
				String[] fields = line.split(";");
				l.add(new Fine(fields[0], fields[1], Integer.parseInt(fields[2])));
			}
			buffer.close();
			return l;
		}catch (IOException e){
			System.out.println("There was an error.");
			return null;
		}
	}
}
