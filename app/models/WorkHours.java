package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

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

    // public Map<String, IrregularDay> irregularDays;
    public Day saturday;
    public Day sunday;

    public Day worrkDay;
}
