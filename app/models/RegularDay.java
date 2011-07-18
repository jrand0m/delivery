package models;

public class RegularDay extends Day {

    DAY_TYPE dayType;

    enum DAY_TYPE {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, HOLLYDAY
    }

}
