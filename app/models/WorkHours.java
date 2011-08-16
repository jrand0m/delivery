package models;

import java.util.Date;
import java.util.Map;

import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.embed.Embedded;

import com.google.gson.JsonObject;

public class WorkHours extends Model {
    @Id
    public Long id;

    @NotNull
    public Client client;
    public String description;
    public Boolean deleted = false;
    public Day worrkDay;
    public Day saturday;
    public Day sunday;

    @Embedded
    public Map<String, IrregularDay> irregularDays;

    public boolean isNowWorking() {
	if (irregularDays.get(new Date().toString()) != null) {
	    IrregularDay irregularDay = irregularDays
		    .get(new Date().toString());
	    if (irregularDay.from.compareTo(new Date().getHours() + "") > 0
		    && irregularDay.to.compareTo(new Date().getHours() + "") < 0) {
		return true;
	    }
	}

	return false;
    }

    public JsonObject getWorkHours() {
	return null;
    }
}
