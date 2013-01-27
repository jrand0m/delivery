/**
 *
 */
package models;

import enumerations.CommentStatus;
import org.joda.time.LocalDateTime;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * @author Mike user can comment once per order(avoid spam)
 */
@Entity
@Table(name = "vd_comments")
@SequenceGenerator(name = "comments_seq_gen", sequenceName = "comments_seq")
public class Comment extends Model {


    @Id
    @GeneratedValue(generator = "comments_seq_gen", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public Long id;

    @Column(name = "comment_text")
    public String text;

    @Column(name = "common_rating")
    public Integer commonRating;

    @Column(name = "commented_at")
    public LocalDateTime commentedAt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public CommentStatus status = CommentStatus.NOT_REVIEWED;
    /**
     * Based on user prefs set default, and ask if to hide his name;
     */
    @Column(name = "show_as_anonymous")
    public boolean showAsAnonymous;

    /**
     * allow only registered users !
     */
    @Column(name = "order_id", nullable = false, insertable = false, updatable = false)
    public Long orderId;
    @OneToOne
    @JoinColumn(name = "order_id")
    @Deprecated
    public Order order;
    @Column(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    public Integer restaurantId;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @Deprecated
    public Restaurant restaurant;

}
