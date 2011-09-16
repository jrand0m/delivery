/**
 * 
 */
package enumerations;

/**
 * @author Mike
 *
 */
public enum OrderStatus {
    OPEN(10), SENT(20), RECIEVED(30), ACCEPTED(40), COOKED(50), DELIVERING(60),  DELIVERED(70),  DECLINED(0);
    private Integer i;
    OrderStatus(Integer ord){
        
    }
    public Integer getOrdinal(){
        return i;
    }
}
