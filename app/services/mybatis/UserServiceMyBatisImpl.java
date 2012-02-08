package services.mybatis;

import com.google.inject.Inject;
import enumerations.UserType;
import models.geo.Address;
import models.users.User;
import services.UserService;
import services.mybatis.mappings.UserMapper;

/**
 * User: Mike Stetsyshyn
 */
public class UserServiceMyBatisImpl implements UserService {

    @Inject
    private UserMapper userMapper;

    @Override
    public User getUserByLogin(String connected) {
        return userMapper.selectUserByLogin(connected);
    }

    @Override
    public void addAddressToUserAddressBook(Address address, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addressIsAssociatedWithUser(Address address, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User insertUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUserInRole(User user, UserType anonymous) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User createAnonymousUser() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean verifyCredentials(String username, String pwd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void touchUser(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean verifyDeviceCredentials(String username, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUserInRole(String connected, UserType courier) {
        throw new UnsupportedOperationException();
    }
}
