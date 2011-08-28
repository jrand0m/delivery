package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REGULAR_DAY")
public class RegularDay extends Day {

    enum DAY_TYPE {
        FRIDAY, HOLLYDAY, MONDAY, SATURDAY, SUNDAY, THURSDAY, TUESDAY, WEDNESDAY
    }

    public DAY_TYPE dayType;

}
