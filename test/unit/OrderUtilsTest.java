package unit;

import helpers.OrderUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import play.test.UnitTest;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 2/16/12
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderUtilsTest extends UnitTest{


    @Test
    public void covertToCents_converts_money(){
        Money money = Money.of(CurrencyUnit.of("UAH"), 15);
        int result  = OrderUtils.convertMoneyToCents(money);
        assertEquals(1500,result);
    }

}
