package models.users;

import enumerations.UserType;
import play.data.validation.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Mike
 * 
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="vd_user")
@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq")
public class User {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "user_seq_gen", strategy = GenerationType.SEQUENCE)
    public Long id;

    @Required
    @Max(255)
    @Min(3)
    @Column(name="vd_login", nullable = false)
    public String login;

    @Email
    @Max(255)
    @Min(3)
    @Column(name="vd_email")
    public String email;

    @Phone
    @Required
    @Max(255)
    @Column(name="vd_phone_number", nullable = false)
    public String phoneNumber;

    @Required
    @Password
    @Column(name="vd_password", nullable = false)
    public String password;

    @Required
    @Max(255)
    @Column(name="vd_name", nullable = false)
    public String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name="vd_user_type")
    public UserType userType;

    @Column(name="last_login_date", nullable = false)
    public Date lastLoginDate;

    @Column(name="created_date", nullable = false)
    public Date createdDate;

    @Column(name="updated_date", nullable = false)
    public Date updatedDate;

    @Column(name="deleted", nullable = false)
	public boolean deleted = false;

	/**
	 * url that will be redirected to after login;
	 * @return relative url or null if no such preference
	 * */
	public String landingUrl(){
        return null;
    };

}