/**
 * 
 */
package models.device;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import enumerations.DeviceStatus;

import play.db.jpa.Model;

/**
 * @author Mike
 * 
 */
@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DEVICE_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GENERIC_DEVICE")
public class GenericDevice extends Model {
    public static final class FIELDS {
	public static final String GENERIC_DEVICE_DEVICEVERSION = "deviceVersion";
	public static final String GENERIC_DEVICE_DEVICEACTIVATEDDATE = "deviceActivatedDate";
	public static final String GENERIC_DEVICE_DEVICE_TYPE = "DEVICE_TYPE";
	public static final String GENERIC_DEVICE_STATUS = "status";
    }
    public boolean deleted = false;
    public String deviceVersion;
    public Date deviceActivatedDate;
    @Column(insertable = false, updatable = false)
    public String DEVICE_TYPE;
    @Enumerated(value = EnumType.STRING)
    public DeviceStatus status = DeviceStatus.DEACTIVATED;
}
