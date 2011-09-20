package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import enumerations.DayType;


import play.Logger;
import play.db.jpa.Model;
import play.i18n.Messages;

@Entity
@Where(clause = "deleted = 0")
public class WorkHours extends Model {
    public static final class FIELDS {
	public static final String WORK_HOURS_DELETED = "deleted";
	public static final String WORK_HOURS_DESCRIPTION = "description";
	public static final String WORK_HOURS_SATURDAY = "saturday";
	public static final String WORK_HOURS_SUNDAY = "sunday";
	public static final String WORK_HOURS_WORRKDAY = "worrkDay";
    }

    public boolean deleted = false;

    public String description;
    @OneToMany(mappedBy = Day.FIELDS.ROOT, fetch=FetchType.EAGER)
    public TreeSet<IrregularDay> irregularDays = new TreeSet<IrregularDay>();
    @OneToMany(mappedBy = Day.FIELDS.ROOT, fetch=FetchType.EAGER)
    public TreeSet<RegularDay> regularDays = new TreeSet<RegularDay>();
    


    public String today() {
	Calendar cal = Calendar.getInstance();
	if (!irregularDays.isEmpty()){
	    IrregularDay id = new IrregularDay();
	    id.day = cal.get(Calendar.DAY_OF_MONTH);
	    id.month = cal.get(Calendar.MONTH)+1;
	    id.day = cal.get(Calendar.YEAR);
	    IrregularDay candidate =  irregularDays.ceiling(id);
	    if ( id.equals(candidate) ){
		return candidate.toString();
	    }
	    Logger.debug("there is exception days(%s)! getting all ", irregularDays.size());
	    irregularDays.floor(id);
	}
	if (!regularDays.isEmpty()){
	    RegularDay d = new RegularDay();
	    d.dayType = DayType.convert(cal.get(cal.DAY_OF_WEEK));
	    
	    d =  regularDays.ceiling(d);
	    if (d != null){
		return d.toString();
	    }
	    
	}
	
	
        return Messages.get("restaurant.workhours.undefined");
    }
}
