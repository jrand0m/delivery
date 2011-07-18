package models;

import java.util.Date;
import java.util.HashMap;

import models.RegularDay.DAY_TYPE;
import siena.Model;

public class WorkHours extends Model {
    HashMap<DAY_TYPE, Day> regularDays;
    HashMap<Date, IrregularDay> irregularDays;
}
