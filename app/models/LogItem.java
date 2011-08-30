package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.Model;

@Entity
public class LogItem extends Model {

    public static void log(String className, String field, String newValue,
            String oldValue, Long classId, String modifiedBy, Date modifiedOn) {
        new LogItem(className, field, newValue, oldValue, classId, modifiedBy,
                modifiedOn).save();
    }

    public static void log(User user, String field, String newValue,
            String oldValue, String modifiedBy) {

        new LogItem(User.class.getName(), field, newValue, oldValue, user.getId(),
                modifiedBy, new Date()).save();
    }
    public Long classId;
    public String className;
    public String field;

    public String modifiedBy;
    public Date   modifiedOn;

    public String newValue;

    public String oldValue;

    public LogItem(String className, String field, String newValue, String oldValue, Long userId, String modifiedBy, Date modifiedOn) {
        super();
        this.className = className;
        this.field = field;
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.classId = userId;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
    }

    /**
     * @param name
     * @param field2
     * @param newValue2
     * @param oldValue2
     * @param id
     * @param modifiedBy2
     * @param modifiedOn2
     */
    
}
