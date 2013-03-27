package models.users;

import enumerations.UserType;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * @author Mike
 */

@Entity
@Table(name = "vd_user")
@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq")
public class User {
    /**
     * @todo switch to UUID and use id field as identifier safely until that query for ID field
     */
    public static String GET_USER_ID_FIELD_NAME(){return "phoneNumber";}
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "user_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    //todo:@Required
    //todo:@Max(255)
    //todo:@Min(3)
    @Column(name = "login", nullable = false, unique = true)
    public String login;

    //todo:@Email
    //todo:@Max(255)
    //todo:@Min(3)
    @Column(name = "email")
    public String email;

    //todo:@Phone
    //todo:@Required
    //todo:@Max(255)
    @Column(name = "phoneNumber", nullable = false, unique = true)
    public String phoneNumber;

    //todo:@Required
    //todo:@Password
    @Column(name = "password", nullable = false)
    public String password;

    //todo:@Required
    //todo:@Max(255)
    @Column(name = "name", nullable = false)
    public String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "userType")
    public UserType userType;

    @Column(name = "lastLoginDate")
    public LocalDateTime lastLoginDate;

    @Column(name = "createdDate", insertable = false, updatable = false)
    public LocalDateTime createdDate;

    @Column(name = "updatedDate")
    public LocalDateTime updatedDate;

    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;

    /**
     * url that will be redirected to after login;
     *
     * @return relative url or null if no such preference
     */
    public String landingUrl() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("User[phone=%s;login=%s]",this.phoneNumber,this.login);    //To change body of overridden methods use File | Settings | File Templates.
    }
}