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

    /**
     * Finds user by specified login
     * @param login - login name(phone number)
     * @return user if found and todo: make it return an object in any case!
     * */
    User getUserByLogin(String login);

    void addAddressToUserAddressBook(Address address, User user);

    void update(User user);

    boolean addressIsAssociatedWithUser(Address address, User user);

    User insertUser(User user);

    boolean isUserInRole(User user, UserType anonymous);

    User createAnonymousUser();

    Long verifyCredentials(String username, String pwd);

    void touchUser(String username);

    boolean verifyDeviceCredentials(String username, String password);

    boolean isUserInRole(String connected, UserType courier);
}
