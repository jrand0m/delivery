package services.mybatis.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.money.Money;
import play.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mike
 */
public class MoneyTypeHandler implements TypeHandler<Money> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        Logger.debug("got %s, truncated to %s", parameter.toString(),parameter.toString().substring(3));
        ps.setString(i, parameter.toString().substring(3));
    }

    @Override
    public Money getResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return  Money.parse(s);
    }

    @Override
    public Money getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return  Money.parse(s);
    }
}
