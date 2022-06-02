package forms.processing;

import java.util.*;

/**
 * DataAggregator class
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es)
 *
 */
public class DataAggregator {
	private Map<String, List<Comparable<?>>> data = new LinkedHashMap<>();
	
	/**
	 * Add a new map of answers to the data
	 * @param answers map of answers to some questions
	 */
	public void add(Map<String, Comparable<?>> answers) {
		answers.entrySet().forEach(e -> this.addToData(e.getKey(), e.getValue()));
		data.values().forEach((List l) -> Collections.sort(l));
	}
	
	/**
	 * Adds a value to the list of data (puts the key in the map if it does not exist) 
	 * @param key key
	 * @param value value
	 */
	private void addToData(String key, Comparable<?> value) {
		if(!(data.containsKey(key))) {
			data.put(key, new ArrayList<>());
		}
		data.get(key).add(value);
	}
	
	/**
	 * The data of the data aggregator
	 * @return the information of the aggregator
	 */
	@Override public String toString() {
		return data.toString();
	}
}
