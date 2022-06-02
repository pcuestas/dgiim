package app.theater.stats;

import java.util.Comparator;

/**
 * Ennumeration StatComparator
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public enum StatComparator implements Comparator<Stat> {   
    /**sort by revenue*/
    REVENUE{
        @Override
        public int compare(Stat o1, Stat o2){
            return compareDoubles(o1.getRevenue(), o2.getRevenue());
        }
    },
    /**sort by attendance*/
    ATTENDANCE{
        @Override
        public int compare(Stat o1, Stat o2){
            return compareDoubles(o1.getAttendancePercentage(),
                                  o2.getAttendancePercentage());            
        }
    },
    /**group by event */
    EVENT{
        @Override
        public int compare(Stat o1, Stat o2){
            if(o1.getEvent()==null || o2.getEvent()==null){
                return 0;
            }
            return o1.getEvent().getTitle().compareTo(o2.getEvent().getTitle());            
        }
    },
    /**group by performance */
    PERFORMANCE{
        @Override
        public int compare(Stat o1, Stat o2){
            if(o1.getPerformance()==null || o2.getPerformance()==null){
                return 0;
            }
            return o1.getPerformance().getDate().compareTo(o2.getPerformance().getDate());            
        }
    },

    /**group by area */
    AREA{
        @Override
        public int compare(Stat o1, Stat o2){
            if(o1.getArea()==null || o2.getArea()==null){
                return 0;
            }
            return o1.getArea().getName().compareTo(o2.getArea().getName());           
        }
    };

    /**
     * Compares two doubles
     * @param d1 first double
     * @param d2 second double
     * @return -1 if d1<d2, 0 if d1==d2, 1 otherwise
     */
    private static int compareDoubles(double d1, double d2){
        double dif = d1 - d2;
        if(dif<0){
            return -1;
        }
        if(dif>0){
            return 1;
        }
        return 0;        
    }

}
