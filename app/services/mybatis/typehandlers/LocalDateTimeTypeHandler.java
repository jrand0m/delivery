package services.mybatis.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.Logger;

import java.sql.*;

/**
 * User: mike
 * tested on postgresql 8.4, 9.1
 */

public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, LocalDateTime localDateTime, JdbcType jdbcType) throws SQLException {
        if (localDateTime == null){
            preparedStatement.setNull(i, Types.TIMESTAMP);
            return;
        }
        DateTimeFormatter format = DateTimeFormat.forPattern(DATE_TIME_PATTERN);

        String formattedString = format.print(localDateTime);
        Logger.debug("Setting col '%d' to %s [%s]", i ,localDateTime.toString(), formattedString );
        preparedStatement.setTimestamp(i, Timestamp.valueOf(formattedString));
    }

    @Override
    public LocalDateTime getResult(ResultSet resultSet, String s) throws SQLException {
        Date sd = resultSet.getDate(s);
        LocalDateTime localDateTime =  new LocalDateTime(sd);
        return localDateTime;
    }

    @Override
    public LocalDateTime getResult(ResultSet resultSet, int i) throws SQLException {
        Date sd = resultSet.getDate(i);
        LocalDateTime localDateTime =  new LocalDateTime(sd);
        return localDateTime;
    }

    @Override
    public LocalDateTime getResult(CallableStatement callableStatement, int i) throws SQLException {
        Date sd = callableStatement.getDate(i);
        LocalDateTime localDateTime =  new LocalDateTime(sd);
        return localDateTime;
    }
}