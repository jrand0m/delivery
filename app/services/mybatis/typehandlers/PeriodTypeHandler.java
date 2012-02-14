package services.mybatis.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mike
 * db must produce ISO_8601 standard period formatting
 */
public class PeriodTypeHandler implements TypeHandler<Period> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Period parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Period getResult(ResultSet rs, String columnName) throws SQLException {
        return ISOPeriodFormat.standard().parsePeriod(rs.getString(columnName));
    }

    @Override
    public Period getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ISOPeriodFormat.standard().parsePeriod(cs.getString(columnIndex));
    }
}
