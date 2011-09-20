/**
 * 
 */
package enumerations;

import java.util.Calendar;

/**
 * @author Mike
 * 
 */
public enum DayType {
    MONDAY,TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

   	public static DayType convert(int calendarDay){
   	 DayType dayType;
   	    switch(calendarDay){
   		case Calendar.TUESDAY:
   		    dayType = TUESDAY;
   		    break;
   		case Calendar.WEDNESDAY:
   		    dayType = WEDNESDAY;
   		    break;
   		case Calendar.THURSDAY:
   		    dayType = THURSDAY;
   		    break;
   		case Calendar.FRIDAY:
   		    dayType= FRIDAY;
   		    break;
   		case Calendar.SATURDAY:
   		    dayType = SATURDAY;
   		    break;
   		case Calendar.SUNDAY:
   		    dayType = SUNDAY;
   		    break;
   		default:
   		case Calendar.MONDAY:
   		    dayType = MONDAY;
   		    break;

   		}
   	    return dayType;
   	    
   	}
   	public static int convert(DayType typeDay){
   	    int calendar;
   	    switch(typeDay){
   		case TUESDAY:
   		    calendar = Calendar.TUESDAY;
   		    break;
   		case WEDNESDAY:
   		    calendar = Calendar.WEDNESDAY;
   		    break;
   		case THURSDAY:
   		    calendar = Calendar.THURSDAY;
   		    break;
   		case FRIDAY:
   		    calendar= Calendar.FRIDAY;
   		    break;
   		case SATURDAY:
   		    calendar = Calendar.SATURDAY;
   		    break;
   		case SUNDAY:
   		    calendar = Calendar.SUNDAY;
   		    break;
   		default:
   		case MONDAY:
   		    calendar = Calendar.MONDAY;
   		    break;

   		}
   	    return calendar;
   	    
   	}
}