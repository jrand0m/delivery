/**
 * 
 */
package enumerations;

/**
 * @author Mike
 * 
 */
public enum OrderStatus {
    /**
     * Starting opint of order
     * */
	OPEN(), 
	/**
	 * User confirms order
	 * */
	SENT(), 
	
	/**
	 * Device recieved/courier approved
	 * */
	CONFIRMED(), 
	/**
	 * Caffe is cooking
	 * */
	ACCEPTED(), 
	/**
	 * Caffe have cooked
	 * */
	COOKED(), 
	/**
	 * Caffe passed to courier
	 * */
	DELIVERING(), 
	/**
	 * Courier delivered to user
	 * */
	DELIVERED(), 
	/**
	 * canceled order *
	 */
	DECLINED();

    /*
     * OrderStatus(Integer ord){
     * 
     * } public Integer getOrdinal(){ return i; }
     */
}
