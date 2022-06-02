package forms.mains;

import java.io.IOException;

import forms.Form;
import forms.fields.Field;
import forms.processing.DataAggregator;

/**
 * Auxiliary class provided by the professors
 */
class Address implements Comparable<Address> {
	private String address;
	private int postalCode;

	public Address(String adr, int pc) {
		this.address = adr;
		this.postalCode = pc;
	}

	public int postalCode() {
		return this.postalCode;
	}

	public String toString() {
		return this.address + " at PC(" + this.postalCode + ")";
	}

	@Override
	public int compareTo(Address o) {
		if(this.postalCode != o.postalCode) {
			return this.postalCode - o.postalCode;
		}
		return this.address.compareTo(o.address);
	}
}

/**
 * The main to try the data aggregator
 * 
 * @author Pablo Cuesta (pablo.cuestas@estudiante.uam.es),
 *         Diego Cid (diego.cid@estudiante.uam.es) 
 *
 */
public class ProcessingMain {
	public static void main(String[] args) throws IOException {
		Form censusForm = new Form();
		Field<Address> adr = new Field<Address>(s -> {
			String[] data = s.split(";");
			return new Address(data[0], Integer.valueOf(data[1].trim()));
		}).addValidation(a -> a.postalCode() >= 0, "Postal code should be positive");
		
		Field<Integer> np = new Field<Integer>(s -> Integer.valueOf(s)).addValidation(s -> s > 0,
				"value greater than 0 expected");
		censusForm.add("Enter address and postal code separated by ';'", adr)
				.add("Number of people living in that address?", np);
		
		DataAggregator dag = new DataAggregator();
		for (int i = 0; i < 3; i++)
			dag.add(censusForm.exec());
		
		System.out.println(dag);
	}
}