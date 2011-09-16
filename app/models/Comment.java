/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.Entity;

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
    public Order	order;
    
    public Client 	client;
    
    public String 	text;
    public Integer 	commonRating;
    public Date		date;
    public CommentStatus status = CommentStatus.NOT_REVIEWED;
    
    public static enum CommentStatus {
	
	NOT_REVIEWED, APPROVED,  REJECTED
	
    }
}
