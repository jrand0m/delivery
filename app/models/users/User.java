package models.users;

import enumerations.UserType;
import org.joda.time.LocalDateTime;
import play.data.validation.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Mike
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "vd_user")
@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "user_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Required
    @Max(255)
    @Min(3)
    @Column(name = "login", nullable = false)
    public String login;

    @Email
    @Max(255)
    @Min(3)
    @Column(name = "email")
    public String email;

    @Phone
    @Required
    @Max(255)
    @Column(name = "phoneNumber", nullable = false)
    public String phoneNumber;

    @Required
    @Password
    @Column(name = "password", nullable = false)
    public String password;

    @Required
    @Max(255)
    @Column(name = "name", nullable = false)
    public String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "userType")
    public UserType userType;

    @Column(name = "lastLoginDate", nullable = false)
    public LocalDateTime lastLoginDate;

    @Column(name = "createdDate", nullable = false)
    public LocalDateTime createdDate;

    @Column(name = "updatedDate", nullable = false)
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
}