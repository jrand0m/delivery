/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;
import enumerations.CommentStatus;

/**
 * @author Mike user can comment once per order(avoid spam)
 */
@Entity
public class Comment extends Model {
    public static final class FIELDS {
	public static final String COMMENT_ORDER = "order";
	public static final String COMMENT_RESTAURANT = "restaurant";
	public static final String COMMENT_TEXT = "text";
	public static final String COMMENT_COMMON_RATING = "commonRating";
	public static final String COMMENT_DATE = "date";
	public static final String COMMENT_STATUS = "status";
	public static final String COMMENT_SHOW_AS_ANONYMOUS = "showAsAnonymous";
    }

    /**
     * allow only registered users !
     * */
    @OneToOne
    public Order order;
    @ManyToOne
    public Restaurant restaurant;

    public String text;
    public Integer commonRating;
    public Date date;
    @Enumerated(value = EnumType.STRING)
    public CommentStatus status = CommentStatus.NOT_REVIEWED;
    /**
     * Based on user prefs set default, and ask if to hide his name;
     * */
    public boolean showAsAnonymous;

}
