package models;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

import com.google.gson.JsonObject;

@Entity
@Where(clause = "deleted = 0")
public class WorkHours extends Model {
    public boolean                   deleted = false;

    public String                    description;
    //@ManyToMany
    //public Map<String, IrregularDay> irregularDays;
    public Day                       saturday;
    public Day                       sunday;

    public Day                       worrkDay;

    public JsonObject getWorkHours() {
        return null;
    }
}
