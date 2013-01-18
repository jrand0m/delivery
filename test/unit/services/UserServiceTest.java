package unit.services;

import enumerations.UserType;
import models.users.User;
import static org.hamcrest.core.IsEqual.equalTo;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Test;
import play.Logger;
import play.libs.Crypto;
import play.modules.guice.InjectSupport;
import play.test.UnitTest;
import services.UserService;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * User: Mike Stetsyshyn
 */
@InjectSupport
public class UserServiceTest {
    @Inject
    private static UserService service;

    @Test
    public void getUserByLogin_ReturnsFullUserInstanceTest(){
        User user = service.getUserByLogin("mickey123");
        assertNotNull(user);
        assertThat(user.id, equalTo(new Long(43)));
        assertThat(user.login, equalTo("mickey123"));
        assertThat(user.email, equalTo("jays.demons@gmail.com"));
        assertThat(user.phoneNumber,equalTo("+380630683088"));
        assertThat(user.password, equalTo(Crypto.passwordHash("password31415", Crypto.HashType.SHA1)));
        assertThat(user.name, equalTo("Mickey The Mouse"));
        assertThat(user.userType,equalTo(UserType.VD_ADMIN));
        assertNotNull(user.createdDate);
        assertNotNull(user.updatedDate);
        assertNotNull(user.lastLoginDate);
        assertFalse(user.deleted);
    }
    
    @Test
    public void insertUser_CreatesRecordAndReturnsInsertedInstanceTest(){
        LocalDateTime dt = new LocalDateTime();
        User newUser = new User();
        newUser.login = "dummy";
        newUser.email =  "dummyMail@mailme.not";
        newUser.phoneNumber = "+380636669966";
        newUser.password = "blah";
        newUser.name  = "Dummy The Dumb";
        newUser.userType = UserType.ANONYMOUS;
        newUser.createdDate = dt;
        newUser.updatedDate = dt;
        newUser.lastLoginDate = dt;
        newUser.deleted = false;
        newUser = service.insertUser(newUser);
        assertNotNull(newUser);
        assertNotNull(newUser.id);
        assertThat("dummy", newUser.login);
        assertThat("dummyMail@mailme.not", newUser.email);
        assertThat("+380636669966", newUser.phoneNumber);
        assertThat("blah", newUser.password);
        assertThat("Dummy The Dumb", newUser.name);
        assertThat(UserType.ANONYMOUS, newUser.userType);
        assertNotNull(newUser.createdDate);
        assertFalse("Create date should be set by db",dt.equals(newUser.createdDate));
        assertNotNull(newUser.updatedDate);
        assertFalse("Update date should be set by db",dt.equals(newUser.updatedDate));
        //assertThat(dt,newUser.lastLoginDate);
        assertThat(Boolean.FALSE, newUser.deleted);
    }

    @Test
    public void createAnonymous_createsAnonUser(){
        User u =  service.createAnonymousUser();
        assertNotNull(u);
        assertNotNull(u.id);
        assertNotNull(u.login);
        assertNotNull(u.email);
        assertNotNull(u.password);
        assertThat(u.password, equalTo(Crypto.passwordHash(u.login, Crypto.HashType.SHA1)));
        assertThat(u.phoneNumber,equalTo(u.login));
        assertFalse(u.deleted);
        User u2 = service.getUserByLogin(u.login);
        assertNotNull(u2);
        assertThat(u2.userType, equalTo(UserType.ANONYMOUS));
    }

    @Test
    public void insertUser_InsertsFieldsWithDefaultValues(){
        User newUser = new User();
        assertFalse("TODO",true);
    }
    @Test
    public void verifyCredentials_verifies_credentials(){
        User newUser = new User();
        assertFalse("TODO",true);
    }

    @Test
    public void touchUser_ChangesLastLoginDate(){
        User u = service.getUserByLogin("mickey123");
        assertNotNull(u);
        LocalDateTime t = u.lastLoginDate;
        assertNotNull(t);
        service.touchUser(u.login);
        u = service.getUserByLogin(u.login);
        assertNotNull(u);
        assertTrue(u.lastLoginDate.isAfter(t));
    }

    @Test
    public void isUserInRole_ChecksUserInRole(){
        int matches = 0;
        for (UserType t : UserType.values()){
            if (service.isUserInRole("mickey123", t)){
               matches= matches +1;
            }
        }
        assertThat(matches, equalTo(1));

    }




}
