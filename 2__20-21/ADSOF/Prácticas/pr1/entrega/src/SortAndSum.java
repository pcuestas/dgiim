import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Keeps two ordered sets of integer numbers, separated in the even set, and the odd set
 * @author Pablo Cuesta pablo.cuestas@estudiante.uam.es, Diego Cid diego.cid@estudiante.uam.es
 */
public class SortAndSum {
    
    private SortedSet<Integer> even= new TreeSet<>(); // the set of even integers
    private SortedSet<Integer> odd= new TreeSet<>(); //the set of odd integers
    private int sumE; // the sum of even numbers
    private int sumO; // the sum of odd numbers (so as not to make a summation of all of the integers of the set when invoking getOddSum(), this is a bit more efficient)

    /**
     * Constructor
     * @param params a list of strings to be converted to integer and inserted
     */
    public SortAndSum(String ... params){
        sumE=0;
        sumO=0;
        for(String s:params){
            int n=Integer.parseInt(s);
            if(n%2==0){
                even.add(n);
                sumE+=n;
            }
            else{
                odd.add(n);
                sumO+=n;
            }
        }
    }

    /**
     * adds a new integer to the object
     * @param i integer to be added
     */
    public void add(int i){
        if(i%2==0){
            even.add(i);
            sumE+=i;
        }
        else{
            odd.add(i);
            sumO+=i;
        }
    }

    /**
     * Returns the sum of all of the even numbers
     * @return the sum of the even numbers
     */
    public int getEvenSum(){
        return this.sumE;
    }

    /**
     * Returns the sum of all of the odd nnumbers
     * @return the sum of the odd numbers
     */
    public int getOddSum(){
        return this.sumO;
    }

    /**
     *
     * @return String representing this object
     */
    public String toString(){
        return "Even numbers "+this.even.toString()+
                " (sum: "+this.getEvenSum()+")"+
                "\nOdd numbers "+this.odd.toString()+
                " (sum: "+this.getOddSum()+")\n";
    }

    /**
     * This function prints the information of the list of integers entered as argument
     * @param args the list of numbers to add to the object
     * 
     * 
    */
    public static void main(String... args) {
        if (args.length<2) {
            System.out.println("At least two numbers are expected");
            System.out.println("Returns the ordered even and odd numbers without repetition and their sum");
        }
        else {
            SortAndSum c = new SortAndSum(args);
            System.out.println(c); // We print the sorted sets and their sum, through the console
            c.add(30); // method to add a number
            c.add(33); // Add number 33
            System.out.println(c); // We print the sorted sets and their sum
            System.out.println("Sum even numbers: "+c.getEvenSum()); // We print the sum even numbers
            System.out.println("Sum odd numbers: "+c.getOddSum()); // We print the sum odd numbers
            System.out.println("Total sum: "+(c.getEvenSum()+c.getOddSum())); // total sum
        }
    }
}