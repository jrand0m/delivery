/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

/**
 * @author Mike
 *	user can comment once per order(avoid spam)
 */
@Entity
public class Comment extends Model {
    
    /**
     * allow only registered users !
     * */
    @OneToOne
    public Order	order;
    @OneToOne
    public Client 	client;
    
    public String 	text;
    public Integer 	commonRating;
    public Date		date;
    public CommentStatus status = CommentStatus.NOT_REVIEWED;
    /**
     * Based on user prefs set default, and ask if to hide his name;
     * */
    public boolean showAsAnonymous;
    public static enum CommentStatus {
	
	NOT_REVIEWED, APPROVED,  REJECTED
	
    }
}
