package helpers;

import models.Courier;
import models.Restaurant;
import models.settings.RestaurantSetting;
import models.settings.CourierSetting;
import models.settings.SystemSetting;
import models.settings.UserSetting;
import models.users.EndUser;

/**
 * 1) @deprecated this is temporary implementation of interface, consider
 * changing implementation to be reliable in cloud environment!
 * 
 * 2) each method must handle them self a default value extraction/putting
 * 
 * 
 * */

public class PropertyVault {

    public static String getSystemValueFor(String key) {
	String value = null;
	SystemSetting obj = SystemSetting.find(SystemSetting.FIELDS.SYSTEMSETTING_stg_key + " = ? and "+SystemSetting.FIELDS.SYSTEMSETTING_ISDEFAULT_SETTING+" = ? ",
		key, false).first();
	if (obj != null) {
	    value = obj.stg_value;
	} else {
	    obj = SystemSetting.find(SystemSetting.FIELDS.SYSTEMSETTING_stg_key+"= ? and "+SystemSetting.FIELDS.SYSTEMSETTING_ISDEFAULT_SETTING+" = ?", key, true)
		    .first();
	    if (obj != null) {
		value = obj.stg_value;
	    }
	}
	return value;
    }

    public static void setSystemValueFor(String key, String value) {
	setSystemValueFor(key, value, false);
    }

    public static void setSystemValueFor(String key, String value,
	    boolean asDefault) {
	// getInstance().put(key, value);
    }

    public static String getUserValueFor(EndUser user, String key) {
	String value = null;
	UserSetting obj = UserSetting.find("key = ? and user = ?", key, user)
		.first();
	if (obj != null) {
	    value = obj.stg_value;
	} else {
	    obj = CourierSetting.find("key = ? and user = ?", key, null)
		    .first();
	    if (obj != null) {
		value = obj.stg_value;
	    }
	}
	return value;
    }

    public static void setUserValueFor(EndUser user, String key, String value) {
	setUserValueFor(user, key, value, false);
    }

    public static void setUserValueFor(EndUser user, String key, String value,
	    boolean asDefault) {
	// getInstance().put(key, value);
    }

    public static String getRestaurantValueFor(Restaurant restaurant, String key) {
	String value = null;
	RestaurantSetting obj = RestaurantSetting.find(
		"key = ? and courier = ? ", key, restaurant).first();
	if (obj != null) {
	    value = obj.stg_value;
	} else {
	    obj = RestaurantSetting.find("key = ? and restaurant = ?", key,
		    null).first();
	    if (obj != null) {
		value = obj.stg_value;
	    }
	}
	return value;
    }

    public static void setRestaurantValueFor(Restaurant restoraunt, String key,
	    String value) {
	setRestaurantValueFor(restoraunt, key, value, false);
    }

    public static void setRestaurantValueFor(Restaurant restaurant, String key,
	    String value, boolean asDefault) {
	// getInstance().put(key, value);
    }

    public static String getCourierValueFor(Courier courier, String key) {
	String value = null;
	CourierSetting obj = CourierSetting.find("key = ? and courier = ?",
		key, courier).first();
	if (obj != null) {
	    value = obj.stg_value;
	} else {
	    obj = CourierSetting.find("key = ? and courier = ?", key, null)
		    .first();
	    if (obj != null) {
		value = obj.stg_value;

	    }
	}
	return value;
    }

    public static void setCourierValueFor(Courier courier, String key,
	    String value) {
	setCourierValueFor(courier, key, value, false);
    }

    public static void setCourierValueFor(Courier courier, String key,
	    String value, boolean asDefault) {
	// getInstance().put(key, value);
    }

}
