package services;

import models.geo.Address;
import models.users.EndUser;
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

    void addAddressToUserAddressBook(Address address, EndUser user);

    void update(EndUser user);

    boolean addressIsAssociatedWithUser(Address address, EndUser user);
}
