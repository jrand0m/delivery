package helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.Client;
import models.Courier;
import models.User;
import models.settings.ClientSetting;
import models.settings.CourierSetting;
import models.settings.SystemSetting;
import models.settings.UserSetting;

/**
 * 1) @deprecated this is temporary implementation of interface, consider changing implementation
 * to be reliable in cloud environment!   
 * 
 * 2) each method must handle them self a default value extraction/putting
 * 
 * */

public class PropertyVault {
	
	public static String getSystemValueFor(String key){
		String value = null;
		SystemSetting obj = SystemSetting.find("key = ? and isDefault = ? ", key, false).first();
		if (obj != null){
			value = obj.value;
		}else {
			obj = SystemSetting.find("key = ? and isDefault = ?", key, true).first();
			if (obj != null){
				value = obj.value;
			}
		}
		return value;
	}
	public static void setSystemValueFor(String key, String value){
		setSystemValueFor( key, value, false);
	}
	public static void setSystemValueFor(String key, String value, boolean asDefault){
		getInstance().put(key, value);
	}
	public static String getUserValueFor(User user, String key){
		String value = null; 
		UserSetting obj = UserSetting.find("key = ? and user = ?", key, user).first();
		if (obj != null){
			value = obj.value;
		}else {
			obj = CourierSetting.find("key = ? and user = ?", key, null).first();
			if (obj!=null){
				value = obj.value;
			}
		}
		return value;
	}
	public static void setUserValueFor(String key, String value){
		setUserValueFor( key, value, false);
	}
	public static void setUserValueFor(String key, String value, boolean asDefault){
		getInstance().put(key, value);
	}
	public static String getClientValueFor(Client client, String key){
		String value = null;
		ClientSetting obj = ClientSetting.find("key = ? and courier = ? ", key, client).first();
		if (obj != null){
			value = obj.value;
		}else {
			obj = ClientSetting.find("key = ? and client = ?", key, null).first();
			if (obj!=null){
				value = obj.value;
			}
		}
		return value;
	}
	public static void setClientValueFor(String key, String value){
		setClientValueFor( key, value, false);
	}
	public static void setClientValueFor(String key, String value, boolean asDefault){
		getInstance().put(key, value);
	}
	public static String getCourierValueFor(Courier courier, String key){
		String value = null;
		CourierSetting obj = CourierSetting.find("key = ? and courier = ?", key, courier).first();
		if (obj != null){
			value = obj.value;
		} else {
			obj = CourierSetting.find("key = ? and courier = ?", key, null).first();
			if (obj!=null){
				value = obj.value;
				
				/**
				 *  ?????
				 *  Change accordingly to new settings model
				 *  ?????
				 * */
//				CourierSetting cs = new CourierSetting();
//				cs.courier= courier;
//				cs.dateChanged = new Date();
//				cs.key = key;
//				cs.value = value;
//				cs.create();
			}
		}
		return value;
	}
	public static void setCourierValueFor(String key, String value){
		setCourierValueFor( key, value, false);
	}
	public static void setCourierValueFor(String key, String value, boolean asDefault){
		getInstance().put(key, value);
 	}
	

}
