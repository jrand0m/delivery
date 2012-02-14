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

        ps.setString(i, parameter==null? null:parameter.toString());
    }

    @Override
    public Period getResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        if (s == null){return null;}
        return ISOPeriodFormat.standard().parsePeriod(s);
    }

    @Override
    public Period getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        if (s == null){return null;}
        return ISOPeriodFormat.standard().parsePeriod(s);
    }
}
