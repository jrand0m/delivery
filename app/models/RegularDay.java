package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REGULAR_DAY")
public class RegularDay extends Day {
    public static final class FIELDS {
	public static final String REGULARDAY_DAYTYPE = "dayType";
    }

    enum DAY_TYPE {
	FRIDAY, HOLLYDAY, MONDAY, SATURDAY, SUNDAY, THURSDAY, TUESDAY, WEDNESDAY
    }

    public DAY_TYPE dayType;

}
