package services.mybatis.mappings;

import models.users.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * User: Mike Stetsyshyn
 */
public interface UserMapper {

    @Select("select * from vd_user as u where u.login = #{1}")
    User selectUserByLogin(String login);

    @Select("insert into vd_user (\"login\", \"email\", \"phoneNumber\", \"password\", \"name\", \"userType\", \"lastLoginDate\", \"deleted\") values ( " +
            "#{login}, #{email}, #{phoneNumber}, #{password}, #{name}, #{userType},  #{lastLoginDate}, #{deleted}  ) RETURNING *")
    User insertUser(User user);
}
