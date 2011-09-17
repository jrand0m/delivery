package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class WorkHours extends Model {
    public static final class FIELDS{
	public static final String DELETED= "deleted";
	public static final String DESCRIPTION = "description";
	public static final String SATURDAY= "saturday";
	public static final String SUNDAY= "sunday";
	public static final String WORRKDAY= "worrkDay";
}
    public boolean                   deleted = false;

    public String                    description;
    
    //public Map<String, IrregularDay> irregularDays;
    public Day                       saturday;
    public Day                       sunday;

    public Day                       worrkDay;
}
