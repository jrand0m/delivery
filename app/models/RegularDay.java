package models;

import java.util.Calendar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import enumerations.DayType;

@Entity
@DiscriminatorValue("REGULAR_DAY")
public class RegularDay extends Day implements Comparable<RegularDay> {
    public static final class FIELDS {
	public static final String REGULARDAY_DAYTYPE = "dayType";
    }


    @Enumerated(value = EnumType.STRING)
    public DayType dayType;

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(RegularDay o) {
	if (equals(o)){
	    return 0;    
	}
	if (dayType !=null){ 
	    return dayType.compareTo(o.dayType);
	}
	return -1;
    }
    
    /* (non-Javadoc)
     * @see models.Day#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        
        return /*super.equals(other) &&*/ other instanceof RegularDay? ((RegularDay) other).dayType == dayType:false;
    }

}
