package services.ebean;

import com.avaje.ebean.Ebean;
import enumerations.UserType;
import helpers.Crypto;
import models.geo.Address;
import models.users.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import play.Logger;
import services.UserService;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceEbeanImpl implements UserService{
    static private String ANON_PREF="Anonymous_";
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
        user.password = StringUtils.defaultString(Crypto.passwordHash(user.password));
        user.createdDate = null; // this field will be set by db
        user.updatedDate = new LocalDateTime(); // this field will be set by db
        user.lastLoginDate = null; //for new user there is no login date until login
        Ebean.save(user);
        Ebean.update(user, new HashSet<String>(){{
            add("createdDate");
            add("updatedDate");
        }});
        return user;
    }

    @Override
    public boolean isUserInRole(User user, UserType anonymous) {
        throw new UnsupportedOperationException("Implement Me");
    }

    @Override
    public User createAnonymousUser() {
        User anon = new User();
        anon.login = ANON_PREF + RandomStringUtils.randomAlphanumeric(10);
        anon.email = StringUtils.EMPTY;
        anon.password = anon.login; // todo:do i need this ?  maybe use empty string
        anon.name = anon.login;
        anon.phoneNumber = anon.login;
        anon.userType = UserType.ANONYMOUS;
        if (Logger.isDebugEnabled()){
            Logger.debug(String.format("Created anonymous user -> %s", anon));
        }
        return insertUser(anon);
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
