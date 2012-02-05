package helpers;

import controllers.Application;
import org.joda.money.Money;


public class OrderUtils {

    /**
     * @param count
     * @return value within range [1;64]
     */
    public static Integer normalizeCount(Integer count) {
        count = Math.abs(count);
        if (count > Application.MAX_ITEM_COUNT_PER_ORDER) {
            return Application.MAX_ITEM_COUNT_PER_ORDER;
        } else if (count == 0) {
            return 1;
        }
        return count;
    }

    public static Long convertToMinutes(long milliseconds) {
        return milliseconds / 1000 / 60;

    }
    public static Integer convertMoneyToCents(Money m){
        return m.getAmountMajorInt()*100 + m.getAmountMinorInt();
    }

}
