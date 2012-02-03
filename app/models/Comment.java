/**
 *
 */
package models;

import enumerations.CommentStatus;
import play.db.jpa.Model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * @author Mike user can comment once per order(avoid spam)
 */
public class Comment {
    public static final class FIELDS {
        public static final String COMMENT_ORDER = "order";
        public static final String COMMENT_RESTAURANT = "restaurant";
        public static final String COMMENT_TEXT = "text";
        public static final String COMMENT_COMMON_RATING = "commonRating";
        public static final String COMMENT_DATE = "date";
        public static final String COMMENT_STATUS = "status";
        public static final String COMMENT_SHOW_AS_ANONYMOUS = "showAsAnonymous";
    }

    public static final class HQL {

        public static final String BY_RESTAURANT_ORDERBY_DATE_DESC = FIELDS.COMMENT_RESTAURANT + " =? order by " + FIELDS.COMMENT_DATE + " desc";

    }

    /**
     * allow only registered users !
     */
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
     */
    public boolean showAsAnonymous;

}
