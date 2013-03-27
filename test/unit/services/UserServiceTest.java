package unit.services;

import com.avaje.ebean.Ebean;
import com.google.inject.Guice;
import enumerations.UserType;
import guice.ServicesConfigurationModule;
import helpers.Crypto;
import models.users.User;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import services.UserService;

import javax.inject.Inject;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * User: Mike Stetsyshyn
 */

public class UserServiceTest {
    @Inject
    private UserService service = Guice.createInjector(new ServicesConfigurationModule()).getInstance(UserService.class);

    @Test
    public void injectsUserServiceTest() {
        assertNotNull("Service did not instanate", service);
    }

    @Test
    public void getUserByLogin_ReturnsFullUserInstanceTest() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                User user = service.getUserByLogin("+380630683088");
                assertNotNull("User must not be null", user);
                assertThat("User Id must be set", user.id, equalTo(new Long(43)));
                assertThat("User login must be set", user.login, equalTo("mickey123"));
                assertThat("user email must be set", user.email, equalTo("jays.demons@gmail.com"));
                assertThat("user phoneNumber Must be set", user.phoneNumber, equalTo("+380630683088"));
                assertThat("user passWord mst be set", user.password, equalTo(Crypto.passwordHash("password31415")));
                assertThat("user name must be set", user.name, equalTo("Mickey The Mouse"));
                assertThat(user.userType, equalTo(UserType.VD_ADMIN));
                assertNotNull(user.createdDate);
                assertNotNull(user.updatedDate);
                assertNotNull(user.lastLoginDate);
                assertFalse(user.deleted);
            }
        });

    }

    @Test
    public void insertUser_CreatesRecordAndReturnsInsertedInstanceTest() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Ebean.beginTransaction();
                try {
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
                    assertNotNull(newUser.id);
                    newUser = service.getUserByLogin("+380636669966");
                    assertNotNull("must return inserted user", newUser);
                    assertThat(newUser.login, equalTo("dummy"));
                    assertThat(newUser.email, equalTo("dummyMail@mailme.not"));
                    assertThat(newUser.phoneNumber, equalTo("+380636669966"));
                    assertThat("password must be encrypted", newUser.password, equalTo(Crypto.passwordHash("blah")));
                    assertThat(newUser.name, equalTo("Dummy The Dumb"));
                    assertThat("Default user type must retain", UserType.ANONYMOUS, equalTo(newUser.userType));
                    assertThat("Create date should be set by db", newUser.createdDate, not(equalTo(dt)));
                    assertThat("Update date should be set by db", newUser.updatedDate, not(equalTo(dt)));
                    assertThat("Last login must be null for new user ",newUser.lastLoginDate, nullValue(LocalDateTime.class));
                    assertThat(Boolean.FALSE, equalTo(newUser.deleted));
                } finally {
                    Ebean.rollbackTransaction();

                }
            }
        });

    }

    @Test
    public void createAnonymous_createsAnonUser() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                User u = service.createAnonymousUser();
                assertNotNull(u);
                assertThat("Anon user must have an id ", u.id, allOf(notNullValue(Long.class)));
                assertThat("login is not null and is not empty", u.login, allOf(notNullValue(String.class), not(equalTo(""))));
                assertThat("email is empty but not null", u.email, equalTo(""));
                assertThat("Default password for anon is its generated name", u.password, equalTo(Crypto.passwordHash(u.login)));
                assertThat("Default phone number for anon is its name", u.phoneNumber, equalTo(u.login));
                assertThat("Anon user must be saved as active user", u.deleted, equalTo(Boolean.FALSE));
                User u2 = service.getUserByLogin(u.login);
                assertThat("User must be accessible by login or phoneNumber", u2, notNullValue(User.class));
                assertThat("USer must be saved as anonymous", u2.userType, equalTo(UserType.ANONYMOUS));
            }
        });
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
