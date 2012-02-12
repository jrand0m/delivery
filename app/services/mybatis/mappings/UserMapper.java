package services.mybatis.mappings;

import enumerations.UserType;
import models.users.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * User: Mike Stetsyshyn
 */
public interface UserMapper {

    @Select("select * from vd_user as u where u.login = #{1}")
    User selectUserByLogin(String login);

    @Select("insert into vd_user (\"login\", \"email\", \"phoneNumber\", \"password\", \"name\", \"userType\", \"lastLoginDate\", \"deleted\") values ( " +
            "#{login}, #{email}, #{phoneNumber}, #{password}, #{name}, #{userType},  #{lastLoginDate}, #{deleted}  ) RETURNING *")
    User insertUser(User user);

    @Select("select \"userType\" from vd_user as u where u.login = #{1}")
    UserType getUserRoleFor(String login);

    @Update("update vd_user set \"lastLoginDate\" = now() where login = #{1} ")
    void updateLastLoginTimeFor(String login);
}
