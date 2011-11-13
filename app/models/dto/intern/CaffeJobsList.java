/**
 * 
 */
package models.dto.intern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike
 * 
 */
public class CaffeJobsList {
	/**
	 * order id
	 * */
	public String id;
	/**
	 * order confirmed time
	 * */
	public Long time;
	/**
	 * PushStatus toString
	 * */
	public String status;
	/**
	 * set only if status InProgress
	 * */
	public Long timeToFinish;

	public String paymentStatus;

	public Integer price;
	public List<MenuItem> list = new ArrayList<MenuItem>();
	public String from;
	public String to;
	public long timeToDelivered;
	public String phone;
	public String additionalInfo;
	public Integer customerPrice;
}
