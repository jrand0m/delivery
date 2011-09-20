package models;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import play.data.validation.Max;
import play.data.validation.Min;

@Entity
@Where(clause = "deleted = false")
@DiscriminatorValue("IRREGULAR_DAY")
public class IrregularDay extends Day implements Comparable<IrregularDay> {
    public static final class FIELDS {
	public static final String IRREGULARDAY_DELETED = "deleted";
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	
    }
    @Max(31)
    @Min(1)
    public int day;
    @Max(12)
    @Min(1)
    public int month;
    @Max(2020)
    @Min(2011)
    public int year;

    
    public boolean deleted = false;

    @Override
    public int compareTo(IrregularDay o) {
	if (equals(o)){
	    return 0;
	}
	if (o == null){
	    return 1;
	}
	if (year == o.year){
	    if (month == o.month){
		return day-o.day;
	    }else {
		return month - o.month;
	    }
	} else {
	    return  year - o.year;
	}
    }
    
    /* (non-Javadoc)
     * @see play.db.jpa.JPABase#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        
        return /*super.equals(other) &&*/ other instanceof IrregularDay? year == ((IrregularDay)other).year &&  month == ((IrregularDay)other).month && year == ((IrregularDay)other).year : false;
    }
    
}
