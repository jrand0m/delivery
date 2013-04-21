/**
 *
 */
package enumerations;

/**
 * @author Mike
 */
public enum OrderStatus {
    /**
     * Starting opint of order
     */
    OPEN(),
    /**
     * User confirms order
     */
    SENT(),
    /**
     * confirmed validity
     */
    CONFIRMED(),
    /**
     * Device recieved
     */
    RECIEVED(),
    /**
     * Caffe is cooking
     */
    ACCEPTED(),
    /**
     * Caffe have cooked
     */
    COOKED(),
    /**
     * Caffe passed to courier
     */
    DELIVERING(),
    /**
     * Courier delivered to user
     */
    DELIVERED(),
    /**
     * canceled order *
     */
    DECLINED();

    public static OrderStatus convert(String string) {
        if ("OPEN".equalsIgnoreCase(string)) {
            return OPEN;
        } else if ("SENT".equalsIgnoreCase(string)) {
            return SENT;
        } else if ("RECIEVED".equalsIgnoreCase(string)) {
            return RECIEVED;
        } else if ("ACCEPTED".equalsIgnoreCase(string)) {
            return ACCEPTED;
        } else if ("COOKED".equalsIgnoreCase(string)) {
            return COOKED;
        } else if ("DELIVERING".equalsIgnoreCase(string)) {
            return DELIVERING;
        } else if ("DELIVERED".equalsIgnoreCase(string)) {
            return DELIVERED;
        } else if ("DECLINED".equalsIgnoreCase(string)) {
            return DECLINED;
        } else if ("CONFIRMED".equalsIgnoreCase(string)) {
            return CONFIRMED;
        }
        return null;
    }
}
