package services.mybatis;

import com.google.inject.Inject;
import enumerations.UserType;
import helpers.Rand0m;
import models.geo.Address;
import models.users.User;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.joda.time.LocalDateTime;
import org.mybatis.guice.transactional.Transactional;
import play.Logger;
import play.libs.Crypto;
import services.UserService;
import services.mybatis.mappings.UserMapper;

import java.util.List;

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
          login = '^' + Rand0m.getDefault().nextString();
          found = userMapper.selectUserByLogin(login);
        } while (found != null);
        u.login = login;
        u.userType = UserType.ANONYMOUS;
        u.password = Crypto.passwordHash(login, Crypto.HashType.SHA1);
        u.email=login;
        u.phoneNumber=login;
        u.name = login;
        u.lastLoginDate = new LocalDateTime();
        u = userMapper.insertUser(u);
        return u;
    }

    @Override
    public boolean verifyCredentials(String username, String pwd) {
       Logger.debug("Logging in user |%s| with pwd |%s| [%s]", username, pwd, Crypto.passwordHash(pwd, Crypto.HashType.SHA1)) ;
       int r = userMapper.userCount(username, Crypto.passwordHash(pwd, Crypto.HashType.SHA1));
       Logger.debug("returned %s ", r );
       Logger.fatal("NO LOGIN CHECK ACTUALLY IS DONE!");
       return  r==0;
    }

    @Override
    public void touchUser(String username) {
        userMapper.updateLastLoginTimeFor(username);
    }

    @Override
    public boolean verifyDeviceCredentials(String username, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isUserInRole(String connected, UserType courier) {
        return courier.equals(userMapper.getUserRoleFor(connected));
    }
}
