package models.time;

import javassist.compiler.ast.Pair;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.chrono.ISOChronology;

import play.Logger;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.i18n.Messages;
import play.libs.Time.CronExpression;

@Entity
@Table(name = "Restaurant_Workhours")
public class WorkHours extends GenericModel {
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workhours_seq")
	@SequenceGenerator(name = "workhours_seq", sequenceName = "workhours_seq")
	public Long id;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime mon_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime mon_end;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime tue_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime tue_end;

	public LocalTime wed_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime wed_end;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime thu_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime thu_end;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime fri_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime fri_end;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sat_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sat_end;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sun_start;
	@Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sun_end;

	/**
	 * 
	 * This constructor sets from and to for all days
	 * 
	 * format for text is HH:mm
	 * 
	 * */
	public WorkHours(String openfrom, String opento) {
		updateAll(openfrom, opento);
	}

	public Long getId() {
		return id;
	}

	@Override
	public Object _key() {
		return getId();
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
