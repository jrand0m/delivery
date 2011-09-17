/**
 * 
 */
package models.device;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@Where(clause = "deleted = 0")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="DEVICE_TYPE",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("GENERIC_DEVICE")
public class GenericDevice extends Model {
    public static final class FIELDS{
	public static final String DEVICEVERSION= "deviceVersion";
	public static final String DEVICEACTIVATEDDATE = "deviceActivatedDate";
	public static final String DEVICE_TYPE= "DEVICE_TYPE";
	public static final String STATUS= "status";
}
    public String deviceVersion;
    public Date deviceActivatedDate;
    public String DEVICE_TYPE; 
    public DeviceStatus status = DeviceStatus.DEACTIVATED;
}
