package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class WorkHours extends Model {
    public boolean                   deleted = false;

    public String                    description;
    
    //public Map<String, IrregularDay> irregularDays;
    public Day                       saturday;
    public Day                       sunday;

    public Day                       worrkDay;
}
