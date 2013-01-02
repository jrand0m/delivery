package services.mybatis.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import play.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * User: Mike Stetsyshyn
 */
public class UUIDTypeHandler implements TypeHandler<UUID> {
    @Override
    public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        Logger.debug("setting UUID %s", parameter.toString());
        ps.setString(i, parameter.toString());
    }

    @Override
    public UUID getResult(ResultSet rs, String columnName) throws SQLException {
        String read  = rs.getString(columnName);
        UUID uuid = UUID.fromString(read);
        Logger.debug("Read UUID %s, converted ->  ", read, uuid
        .toString());
        return uuid;
    }

    @Override
    public UUID getResult(ResultSet resultSet, int i) throws SQLException {
        String read  = resultSet.getString(i);
        UUID uuid = UUID.fromString(read);
        Logger.debug("Read UUID %s, converted ->  ", read, uuid
                .toString());
        return uuid;
    }

    @Override
    public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String read  = cs.getString(columnIndex);
        UUID uuid = UUID.fromString(read);
        Logger.debug("Read UUID %s, converted ->  ", read, uuid
                .toString());
        return uuid;
    }
}
