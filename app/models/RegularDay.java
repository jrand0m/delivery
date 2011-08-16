package models;

import siena.NotNull;

public class RegularDay extends Day {

    @NotNull
    public DAY_TYPE dayType;

    enum DAY_TYPE {
	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, HOLLYDAY
    }

}
