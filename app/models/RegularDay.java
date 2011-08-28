package models;

import javax.persistence.Entity;

@Entity
public class RegularDay extends Day {

    enum DAY_TYPE {
        FRIDAY, HOLLYDAY, MONDAY, SATURDAY, SUNDAY, THURSDAY, TUESDAY, WEDNESDAY
    }

    public DAY_TYPE dayType;

}
