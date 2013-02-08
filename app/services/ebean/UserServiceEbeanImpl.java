package services.ebean;

import com.avaje.ebean.Ebean;
import enumerations.UserType;
import models.geo.Address;
import models.users.User;
import services.UserService;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceEbeanImpl implements UserService{

    @Override
    public User getUserByLogin(String identifier) {
        return Ebean.find(User.class).where().eq("phoneNumber",identifier).findUnique();
    }

    @Override
    public void addAddressToUserAddressBook(Address address, User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public boolean addressIsAssociatedWithUser(Address address, User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public User insertUser(User user) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public boolean isUserInRole(User user, UserType anonymous) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public User createAnonymousUser() {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public Long verifyCredentials(String username, String pwd) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public void touchUser(String username) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public boolean verifyDeviceCredentials(String username, String password) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public boolean isUserInRole(String connected, UserType courier) {
        throw new UnsupportedOperationException("Implement Me");
    }
}
