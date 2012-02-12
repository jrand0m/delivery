package unit.services;

import enumerations.UserType;
import models.users.User;
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

/**
 * User: Mike Stetsyshyn
 */
@InjectSupport
public class UserServiceTest extends UnitTest {
    @Inject
    private static UserService service;

    @Test
    public void getUserByLogin_ReturnsFullUserInstanceTest(){
        User user = service.getUserByLogin("mickey123");
        assertNotNull(user);
        assertEquals(new Long(43), user.id);
        assertEquals("mickey123", user.login);
        assertEquals("jays.demons@gmail.com", user.email);
        assertEquals("+380630683088", user.phoneNumber);
        assertEquals("password31415", user.password);
        assertEquals("Mickey The Mouse", user.name);
        assertEquals(UserType.VD_ADMIN, user.userType);
        assertNotNull(user.createdDate);
        assertNotNull(user.updatedDate);
        assertNotNull(user.lastLoginDate);
        assertEquals(Boolean.FALSE, user.deleted);
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
        assertEquals("dummy", newUser.login);
        assertEquals("dummyMail@mailme.not", newUser.email);
        assertEquals("+380636669966", newUser.phoneNumber);
        assertEquals("blah", newUser.password);
        assertEquals("Dummy The Dumb", newUser.name);
        assertEquals(UserType.ANONYMOUS, newUser.userType);
        assertNotNull(newUser.createdDate);
        assertFalse("Create date should be set by db",dt.equals(newUser.createdDate));
        assertNotNull(newUser.updatedDate);
        assertFalse("Update date should be set by db",dt.equals(newUser.updatedDate));
        assertTrue(dt.equals(newUser.lastLoginDate));
        assertEquals(Boolean.FALSE, newUser.deleted);
    }

    @Test
    public void createAnonymous_createsAnonUser(){
        User u =  service.createAnonymousUser();
        assertNotNull(u);
        assertNotNull(u.id);
        assertNotNull(u.login);
        assertNotNull(u.email);
        assertNotNull(u.password);
        assertEquals(u.password, Crypto.passwordHash(u.login, Crypto.HashType.SHA1 ));
        assertEquals(u.login, u.phoneNumber);
        assertFalse(u.deleted);
        User u2 = service.getUserByLogin(u.login);
        assertNotNull(u2);
        assertEquals(u2.userType,UserType.ANONYMOUS);
    }

    @Test
    public void insertUser_InsertsFieldsWithDefaultValues(){
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
        assertEquals(1l,matches);

    }




}
