package models;

import com.avaje.ebean.config.CompoundType;
import com.avaje.ebean.config.CompoundTypeProperty;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 1/27/13
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class JodaMoneyCompoundType implements CompoundType<Money> {


    @Override
    public Money create(Object[] propertyValues) {

        return Money.ofMinor(CurrencyUnit.getInstance((String) propertyValues[0]), (Long) propertyValues[1]);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompoundTypeProperty<Money, ?>[] getProperties() {
        CompoundTypeProperty[] result = {new CompoundTypeProperty<Money, String>() {
            @Override
            public String getName() {
                return "currency";
            }

            @Override
            public String getValue(Money valueObject) {
                return valueObject.getCurrencyUnit().getCurrencyCode();
            }

            @Override
            public int getDbType() {
                return 0;
            }
        }, new CompoundTypeProperty<Money, Long>() {
            @Override
            public String getName() {
                return "cents";
            }

            @Override
            public Long getValue(Money valueObject) {
                return valueObject.getAmountMinorLong();
            }

            @Override
            public int getDbType() {
                return 0;
            }
        }
        };
        return result;
    }
}
