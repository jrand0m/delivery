package services.mybatis;

import com.google.inject.Inject;
import enumerations.UserType;
import helpers.Rand0m;
import models.geo.Address;
import models.users.User;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.guice.transactional.Transactional;
import play.libs.Crypto;
import services.UserService;
import services.mybatis.mappings.UserMapper;

/**
 * User: Mike Stetsyshyn
 */
public class UserServiceMyBatisImpl implements UserService {

    @Inject
    private UserMapper userMapper;

    @Override
    @Transactional(isolationLevel = TransactionIsolationLevel.READ_COMMITTED)
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
    @Transactional
    public User insertUser(User user) {
        if (user.id != null) {throw new RuntimeException("when inserting user 'id' field must be null!");}
        return userMapper.insertUser(user);
    }

    @Override
    public boolean isUserInRole(User user, UserType anonymous) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User createAnonymousUser() {
        User u = new User();
        User found = null;
        String login = "";
        do{
          login = '^' + Rand0m.getDefaultRand0m().nextString();
          found = userMapper.selectUserByLogin(login);
        } while (found != null);
        u.login = login;
        u.userType = UserType.ANONYMOUS;
        u.password = Crypto.passwordHash(login);

        return u;
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
