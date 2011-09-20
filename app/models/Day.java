package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DAY_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("BASIC_DAY")
public class Day extends Model {
    public static final class FIELDS {
	public static final String DAY_FROM = "from";
	public static final String DAY_TO = "to";
	public static final String ROOT = "root";
    }
    public static final Day NOTSPECIFIED = new Day();  
    final static public String TIME_FORMAT = "HH:mm";
    @Column(name = "from_time")
    public String from;
    @Column(name = "to_time")
    public String to;
    @ManyToOne
    public WorkHours root; 
    /* (non-Javadoc)
     * @see play.db.jpa.JPABase#toString()
     */
    @Override
    public String toString() {
        return from + " - " + to;
    }
    /**
     * 
     */
    public Day() {

    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see play.db.jpa.JPABase#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
	return super.equals(other) && other instanceof Day? to.equalsIgnoreCase(((Day)other).to) && from.equalsIgnoreCase(((Day)other).from)&& root.equals(((Day)other).root):false ;
    }
}
