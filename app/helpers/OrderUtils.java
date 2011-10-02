package helpers;

import controllers.Application;



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

}
