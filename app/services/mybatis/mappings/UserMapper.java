package services.mybatis.mappings;

import models.users.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * User: Mike Stetsyshyn
 */
public interface UserMapper {

    @Select("select id from vd_user as u where u.login = #{1}")
    User selectUserByLogin(String login);
}
