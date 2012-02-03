package models.time;

import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.joda.time.chrono.ISOChronology;
import play.Logger;
import play.i18n.Messages;

import javax.persistence.*;


@Table(name = "vd_restaurant_workhours")
@SequenceGenerator(name = "workhours_seq_gen", sequenceName = "workhours_seq")
public class WorkHours {
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
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime mon_start;
    @Column(name = "mon_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime mon_end;
    @Column(name = "tue_start")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime tue_start;
    @Column(name = "tue_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime tue_end;
    @Column(name = "wed_start")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime wed_start;
    @Column(name = "wed_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime wed_end;
    @Column(name = "thu_start")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime thu_start;
    @Column(name = "thu_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime thu_end;
    @Column(name = "fri_start")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime fri_start;
    @Column(name = "fri_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime fri_end;
    @Column(name = "sat_start")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime sat_start;
    @Column(name = "sat_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime sat_end;
    @Column(name = "sun_start")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime sun_start;
    @Column(name = "sun_end")
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    public LocalTime sun_end;

    /**
     * This constructor sets from and to for all days
     * <p/>
     * format for text is HH:mm
     */
    public WorkHours(String openfrom, String opento) {
        updateAll(openfrom, opento);
    }

    public String todayAsString() {
        DateTimeZone zone = DateTimeZone.forID("Europe/Kiev");
        Chronology chron = ISOChronology.getInstance(zone);
        LocalDateTime dt = new LocalDateTime(chron);
        String s = Messages.get("restaurant.workhours.undefined");
        switch (dt.getDayOfWeek()) {
            case DateTimeConstants.MONDAY:
                s = mon_start.toString(TIME_FORMAT) + " - " + mon_end.toString(TIME_FORMAT);
                break;
            case DateTimeConstants.TUESDAY:
                s = tue_start.toString(TIME_FORMAT) + " - " + tue_end.toString(TIME_FORMAT);
                break;
            case DateTimeConstants.WEDNESDAY:
                s = wed_start.toString(TIME_FORMAT) + " - " + wed_end.toString(TIME_FORMAT);
                break;
            case DateTimeConstants.THURSDAY:
                s = thu_start.toString(TIME_FORMAT) + " - " + thu_end.toString(TIME_FORMAT);
                break;
            case DateTimeConstants.FRIDAY:
                s = fri_start.toString(TIME_FORMAT) + " - " + fri_end.toString(TIME_FORMAT);
                break;
            case DateTimeConstants.SATURDAY:
                s = sat_start.toString(TIME_FORMAT) + " - " + sat_end.toString(TIME_FORMAT);
                break;
            case DateTimeConstants.SUNDAY:
                s = sun_start.toString(TIME_FORMAT) + " - " + sun_end.toString(TIME_FORMAT);
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
                Logger.warn("Failed to read number for 'from' field ",
                        fex.getMessage());
                fTime = DEFAULT_FROM;
            }

        } else {
            Logger.warn(
                    "Failed to read number for 'from' field defaulting to %d:%d",
                    DEFAULT_FROM_HOUR, DEFAULT_FROM_MINUTES);
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
                Logger.warn("Failed to read number for 'to' field ",
                        fex.getMessage());
                tTime = DEFAULT_TO;
            }

        } else {
            Logger.warn(
                    "Failed to read number for 'to' field defaulting to %d:%d",
                    DEFAULT_TO_HOUR, DEFAULT_TO_MINUTES);
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
