package models.settings;

import java.util.Date;

import javax.persistence.*;

import play.data.validation.Max;
import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
@Table(name = "vd_system_settings")
@SequenceGenerator(name = "system_settings_seq_gen", sequenceName = "system_settings_seq")
public class SystemSetting{

	public static final class KEYS {
		public static final String DEFAULT_CITY_ID = "defaultCityId";
		public static final String GUESS_CITY_ENABLED = "guessCityByIpEnabled";
	}
	public static final class DEFAULT_VALUES{
		public static final long DEFAULT_CITY_ID = 1;
	}
    @Id
    @GeneratedValue(generator = "system_settings_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Integer id;

	@Required
	@Min(3)
	@Max(32)
	@Column(name = "_stg_key")
	public String stg_key;
	@Required
	@Min(1)
	@Column(name = "_stg_value")
	public String stg_value;
	@Required
    @Column(name = "is_default")
	public boolean isDefault = false;
	/**
	 * if null than no date
	 * */
    @Column(name = "start_date")
 	public Date startDate;
	/**
	 * if null than no date
	 * */
    @Column(name = "end_date")
    public Date endDate;
}
