package models.time;


import org.joda.time.*;
import org.joda.time.chrono.ISOChronology;
import play.Logger;
import play.db.ebean.Model;
import play.i18n.Messages;

import javax.persistence.*;

@Entity
@Table(name = "vd_restaurant_workhours")
@SequenceGenerator(name = "workhours_seq_gen", sequenceName = "workhours_seq")
public class WorkHours extends Model {
    public static final int DEFAULT_FROM_HOUR = 8;
    public static final int DEFAULT_FROM_MINUTES = 30;
    public static final int DEFAULT_TO_HOUR = 22;
    public static final int DEFAULT_TO_MINUTES = 00;
    public static final String TIME_FORMAT = "HH:mm";
    public static final LocalTime DEFAULT_FROM = new LocalTime(
            DEFAULT_FROM_HOUR, DEFAULT_FROM_MINUTES);
    public static final LocalTime DEFAULT_TO = new LocalTime(DEFAULT_TO_HOUR,
            DEFAULT_TO_MINUTES);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workhours_seq_gen")
    public Integer id;

    @Column(name = "mon_start")
    public LocalTime mon_start;
    @Column(name = "mon_end")
    public LocalTime mon_end;
    @Column(name = "tue_start")
    public LocalTime tue_start;
    @Column(name = "tue_end")
    public LocalTime tue_end;
    @Column(name = "wed_start")
    public LocalTime wed_start;
    @Column(name = "wed_end")
    public LocalTime wed_end;
    @Column(name = "thu_start")
    public LocalTime thu_start;
    @Column(name = "thu_end")
    public LocalTime thu_end;
    @Column(name = "fri_start")
    public LocalTime fri_start;
    @Column(name = "fri_end")
    public LocalTime fri_end;
    @Column(name = "sat_start")
    public LocalTime sat_start;
    @Column(name = "sat_end")
    public LocalTime sat_end;
    @Column(name = "sun_start")
    public LocalTime sun_start;
    @Column(name = "sun_end")
    public LocalTime sun_end;

    /**
     * This constructor sets from and to for all days
     * <p/>
     * format for text is HH:mm
     */
    public WorkHours(String openfrom, String opento) {
        updateAll(openfrom, opento);
    }

    public String monAsString(){
       return  mon_start.toString(TIME_FORMAT) + " - " + mon_end.toString(TIME_FORMAT);
    }

    public String tueAsString(){
       return tue_start.toString(TIME_FORMAT) + " - " + tue_end.toString(TIME_FORMAT);
    }
    public String wedAsString(){
       return wed_start.toString(TIME_FORMAT) + " - " + wed_end.toString(TIME_FORMAT);
    }
    public String thuAsString(){
        return thu_start.toString(TIME_FORMAT) + " - " + thu_end.toString(TIME_FORMAT);
    }
    public String friAsString(){
        return fri_start.toString(TIME_FORMAT) + " - " + fri_end.toString(TIME_FORMAT);
    }
    public String satAsString(){
        return sat_start.toString(TIME_FORMAT) + " - " + sat_end.toString(TIME_FORMAT);
    }
    public String sunAsString(){
        return sun_start.toString(TIME_FORMAT) + " - " + sun_end.toString(TIME_FORMAT);
    }

    public String todayAsString() {
        DateTimeZone zone = DateTimeZone.forID("Europe/Kiev");
        Chronology chron = ISOChronology.getInstance(zone);
        LocalDateTime dt = new LocalDateTime(chron);
        String s = Messages.get("restaurant.workhours.undefined");
        switch (dt.getDayOfWeek()) {
            case DateTimeConstants.MONDAY:
                s = monAsString();
                break;
            case DateTimeConstants.TUESDAY:
                s =  tueAsString();
                break;
            case DateTimeConstants.WEDNESDAY:
                s = wedAsString();
                break;
            case DateTimeConstants.THURSDAY:
                s = thuAsString();
                break;
            case DateTimeConstants.FRIDAY:
                s = friAsString();
                break;
            case DateTimeConstants.SATURDAY:
                s = satAsString();
                break;
            case DateTimeConstants.SUNDAY:
                s = sunAsString();
                break;
        }
        return s;
    }

    public void updateAll(String openfrom, String opento) {
        String delim = ":";
        String[] t = openfrom.split(delim);
        LocalTime fTime;
        if (t.length == 2) {
            try {
                int oH = Integer.parseInt(t[0]);
                int oM = Integer.parseInt(t[1]);
                fTime = new LocalTime(oH, oM);
            } catch (NumberFormatException fex) {
                Logger.warn(String.format("Failed to read number for 'from' field ",
                        fex.getMessage()));
                fTime = DEFAULT_FROM;
            }

        } else {
            Logger.warn(String.format(
                    "Failed to read number for 'from' field defaulting to %d:%d",
                    DEFAULT_FROM_HOUR, DEFAULT_FROM_MINUTES));
            fTime = DEFAULT_FROM;
        }
        mon_start = fTime;
        tue_start = fTime;
        wed_start = fTime;
        thu_start = fTime;
        fri_start = fTime;
        sat_start = fTime;
        sun_start = fTime;

        t = opento.split(delim);
        LocalTime tTime;
        if (t.length == 2) {
            try {
                int oH = Integer.parseInt(t[0]);
                int oM = Integer.parseInt(t[1]);
                tTime = new LocalTime(oH, oM);
            } catch (NumberFormatException fex) {
                Logger.warn(String.format("Failed to read number for 'to' field ",
                        fex.getMessage()));
                tTime = DEFAULT_TO;
            }

        } else {
            Logger.warn(String.format(
                    "Failed to read number for 'to' field defaulting to %d:%d",
                    DEFAULT_TO_HOUR, DEFAULT_TO_MINUTES));
            tTime = DEFAULT_TO;
        }
        mon_end = tTime;
        tue_end = tTime;
        wed_end = tTime;
        thu_end = tTime;
        fri_end = tTime;
        sat_end = tTime;
        sun_end = tTime;


    }
}
