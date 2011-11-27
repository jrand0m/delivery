package models.time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name="Restaurant_Workhours")
public class WorkHours extends GenericModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workhours_seq")
    @SequenceGenerator(name = "workhours_seq", sequenceName = "workhours_seq")
	public Long id;
    
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime mon_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime mon_end;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime tue_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime tue_end;
	
	public LocalTime wen_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime wen_end;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime thu_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime thu_end;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime fri_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime fri_end;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sat_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sat_end;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sun_start;
	@Type(type="org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
	public LocalTime sun_end;
	
	
	public Long getId() {
        return id;
    }

    @Override
    public Object _key() {
        return getId();
    }
}
