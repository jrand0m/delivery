package unit.services;

import enumerations.UserType;
import models.users.User;
import org.junit.Test;
import play.modules.guice.InjectSupport;
import play.test.UnitTest;
import services.UserService;

import javax.inject.Inject;

/**
 * User: Mike Stetsyshyn
 */
@InjectSupport
public class UserServiceTest extends UnitTest {
    @Inject
    private static UserService service;

    @Test
    public void getUserByLoginReturnsFullUserInstanceTest(){
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

}
