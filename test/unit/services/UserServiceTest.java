package unit.services;

import com.google.inject.Guice;
import enumerations.UserType;
import guice.eBeanApplicationConfigurationModule;
import helpers.Crypto;
import models.users.User;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import play.Logger;
import services.UserService;

import javax.inject.Inject;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * User: Mike Stetsyshyn
 */

public class UserServiceTest {
    @Inject
    private UserService service = Guice.createInjector(new eBeanApplicationConfigurationModule()).getInstance(UserService.class);

    @Test
    public void injectsUserServiceTest() {
        assertNotNull("Fucking fuck doesnt work", service);

    }
    @Ignore
    @Test
    public void getUserByLogin_ReturnsFullUserInstanceTest() {
        User user = service.getUserByLogin("mickey123");
        assertNotNull(user);
        assertThat(user.id, equalTo(new Long(43)));
        assertThat(user.login, equalTo("mickey123"));
        assertThat(user.email, equalTo("jays.demons@gmail.com"));
        assertThat(user.phoneNumber, equalTo("+380630683088"));
        assertThat(user.password, equalTo(Crypto.passwordHash("password31415")));
        assertThat(user.name, equalTo("Mickey The Mouse"));
        assertThat(user.userType, equalTo(UserType.VD_ADMIN));
        assertNotNull(user.createdDate);
        assertNotNull(user.updatedDate);
        assertNotNull(user.lastLoginDate);
        assertFalse(user.deleted);
    }
    @Ignore
    @Test
    public void insertUser_CreatesRecordAndReturnsInsertedInstanceTest() {
        LocalDateTime dt = new LocalDateTime();
        User newUser = new User();
        newUser.login = "dummy";
        newUser.email = "dummyMail@mailme.not";
        newUser.phoneNumber = "+380636669966";
        newUser.password = "blah";
        newUser.name = "Dummy The Dumb";
        newUser.userType = UserType.ANONYMOUS;
        newUser.createdDate = dt;
        newUser.updatedDate = dt;
        newUser.lastLoginDate = dt;
        newUser.deleted = false;
        newUser = service.insertUser(newUser);
        assertNotNull("message?"+service,newUser);
        assertNotNull(newUser.id);
        assertThat("dummy", equalTo(newUser.login));
        assertThat("dummyMail@mailme.not", equalTo(newUser.email));
        assertThat("+380636669966", equalTo(newUser.phoneNumber));
        assertThat("blah", equalTo(newUser.password));
        assertThat("Dummy The Dumb", equalTo(newUser.name));
        assertThat(UserType.ANONYMOUS, equalTo(newUser.userType));
        assertNotNull(newUser.createdDate);
        assertFalse("Create date should be set by db", dt.equals(newUser.createdDate));
        assertNotNull(newUser.updatedDate);
        assertFalse("Update date should be set by db", dt.equals(newUser.updatedDate));
        //assertThat(dt,newUser.lastLoginDate);
        assertThat(Boolean.FALSE, equalTo(newUser.deleted));
    }

    @Ignore
    @Test
    public void createAnonymous_createsAnonUser() {
        User u = service.createAnonymousUser();
        assertNotNull(u);
        assertNotNull(u.id);
        assertNotNull(u.login);
        assertNotNull(u.email);
        assertNotNull(u.password);
        assertThat(u.password, equalTo(Crypto.passwordHash(u.login)));
        assertThat(u.phoneNumber, equalTo(u.login));
        assertFalse(u.deleted);
        User u2 = service.getUserByLogin(u.login);
        assertNotNull(u2);
        assertThat(u2.userType, equalTo(UserType.ANONYMOUS));
    }

    @Ignore
    @Test
    public void insertUser_InsertsFieldsWithDefaultValues() {
        User newUser = new User();
        assertFalse("TODO", true);
    }

    @Ignore
    @Test
    public void verifyCredentials_verifies_credentials() {
        User newUser = new User();
        assertFalse("TODO", true);
    }

    @Ignore
    @Test
    public void touchUser_ChangesLastLoginDate() {
        User u = service.getUserByLogin("mickey123");
        assertNotNull(u);
        LocalDateTime t = u.lastLoginDate;
        assertNotNull(t);
        service.touchUser(u.login);
        u = service.getUserByLogin(u.login);
        assertNotNull(u);
        assertTrue(u.lastLoginDate.isAfter(t));
    }


    @Ignore
    @Test
    public void isUserInRole_ChecksUserInRole() {
        int matches = 0;
        for (UserType t : UserType.values()) {
            if (service.isUserInRole("mickey123", t)) {
                matches = matches + 1;
            }
        }
        assertThat(matches, equalTo(1));

    }


}
