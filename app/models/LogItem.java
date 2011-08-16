package models;

import java.util.Date;


import siena.Id;
import siena.Model;

public class LogItem extends Model {

    @Id
    public Long id;

    public String className;
    public String field;
    public String newValue;
    public String oldValue;
    public Long classId;

    public String modifiedBy;
    public Date modifiedOn;

    public LogItem(String className, String field, String newValue,
	    String oldValue, Long classId, String modifiedBy, Date modifiedOn) {
	super();
	this.className = className;
	this.field = field;
	this.newValue = newValue;
	this.oldValue = oldValue;
	this.classId = classId;
	this.modifiedBy = modifiedBy;
	this.modifiedOn = modifiedOn;
    }

    public static void log(String className, String field, String newValue,
	    String oldValue, Long classId, String modifiedBy, Date modifiedOn) {
	new LogItem(className, field, newValue, oldValue, classId, modifiedBy,
		modifiedOn).insert();
    }

    public static void log(User user, String field, String newValue,
	    String oldValue, String modifiedBy) {

	new LogItem(User.class.getName(), field, newValue, oldValue, user.id,
		modifiedBy, new Date()).insert();
    }
}
