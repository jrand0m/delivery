package services;

import enumerations.UserType;
import models.geo.Address;
import models.users.User;

/**
 * User: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 6:46 PM
 */
public interface UserService {


    User getUserByLogin(String connected);

    void addAddressToUserAddressBook(Address address, User user);

    void update(User user);

    boolean addressIsAssociatedWithUser(Address address, User user);

    User insertUser(User user);

    boolean isUserInRole(User user, UserType anonymous);

    User createAnonymousUser();

    boolean verifyCredentials(String username, String pwd);

    void touchUser(String username);

    boolean verifyDeviceCredentials(String username, String password);

    boolean isUserInRole(String connected, UserType courier);
}
