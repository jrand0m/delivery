package models.settings;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;
@Entity
@Table(name = "SystemSettings")
public class SystemSetting extends Model {

		public String key;
		public String value;
		public Date dateChanged;
		public boolean isDefault;
}
