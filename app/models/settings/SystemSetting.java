package models.settings;

import javax.persistence.*;
import java.util.Date;


@Table(name = "vd_system_settings")
@SequenceGenerator(name = "system_settings_seq_gen", sequenceName = "system_settings_seq")
public class SystemSetting {

    public static final class KEYS {
        public static final String DEFAULT_CITY_ID = "defaultCityId";
        public static final String GUESS_CITY_ENABLED = "guessCityByIpEnabled";
    }

    public static final class DEFAULT_VALUES {
        public static final long DEFAULT_CITY_ID = 1;
    }

    @Id
    @GeneratedValue(generator = "system_settings_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Integer id;

    //TODO:@Required
    //TODO:@Min(3)
    //TODO:@Max(32)
    @Column(name = "_stg_key")
    public String stg_key;
    //TODO:@Required
    //TODO:@Min(1)
    @Column(name = "_stg_value")
    public String stg_value;
    //TODO:@Required
    @Column(name = "isDefault")
    public boolean isDefault = false;
    /**
     * if null than no date
     */
    @Column(name = "startDate")
    public Date startDate;
    /**
     * if null than no date
     */
    @Column(name = "endDate")
    public Date endDate;
}
