package services.mybatis.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.sql.*;

/**
 * User: mike
 */
@SuppressWarnings("deprecated")
public class LocalTimeTypeHandler implements TypeHandler<LocalTime> {
    @Override
    public void setParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType) throws SQLException {
       ps.setTime(i, new Time(parameter.getHourOfDay(),parameter.getMinuteOfHour(),parameter.getSecondOfMinute()) );
    }

    @Override
    public LocalTime getResult(ResultSet rs, String columnName) throws SQLException {
        Time time = rs.getTime(columnName);
        return new LocalTime( time.getHours(), time.getMinutes(),  time.getSeconds());
    }

    @Override
    public LocalTime getResult(ResultSet resultSet, int i) throws SQLException {
        Time time = resultSet.getTime(i);
        return new LocalTime( time.getHours(), time.getMinutes(),  time.getSeconds());

    }

    @Override
    @SuppressWarnings("deprecated")
    public LocalTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Time time = cs.getTime(columnIndex);
        return new LocalTime( time.getHours(), time.getMinutes(),  time.getSeconds());

    }
}
