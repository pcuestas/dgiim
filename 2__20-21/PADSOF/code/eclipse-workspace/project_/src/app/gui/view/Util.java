package app.gui.view;

import java.awt.*;
import javax.swing.*;
/**
 * Util class. Some auxiliary method and classes.
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Util {
    private Util(){}
    
    /** 
     * Set the font of the label as a predefined value
     * @param label the label
     */
    public static void setFont(JLabel label) {
		label.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
	}

    /**
     * Auxiliary class. A pair of integers.
     */
    public static class SittingPair{
        private Integer x;
        private Integer y;
        
        /**
         * Constructor
         * @param xx row
         * @param yy column 
         */
        public SittingPair(int xx, int yy){
            x = xx; y = yy;
        }

        /**
         * equals method
         * @param o object this is compared to 
         * @return whether o is equal
         */
        @Override
        public boolean equals(Object o){
            if(!(o instanceof SittingPair)) return false;
            SittingPair s = (SittingPair)o;
            return (s.x == x) && (s.y == y);
        }

        /**
         * hashCode
         * @return the hashCode
         */
        @Override
        public int hashCode(){
            return x.hashCode()*57 + y.hashCode();
        }

        /**
         * toString
         * @return string
         */
		@Override
		public String toString(){
			return "("+x+", "+y+")";
		}
        /**
         * getter
         * @return the row
         */
		public int getRow(){return x;}
        /**
         * getter
         * @return the column
         */
		public int getColumn(){return y;}
    }
	
}