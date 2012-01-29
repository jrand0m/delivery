package services;

import models.users.User;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

/**
 * BaseUser: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 6:46 PM
 */
public interface UserService {


    User getUserByLogin(String connected);
}
