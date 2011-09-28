/**
 * 
 */
package models.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mike
 * 
 */
public class CaffeJobsList {
    /**
     * order id
     * */
    public String id;
    /**
     * order confirmed time
     * */
    public Long time;
    /**
     * PushStatus toString
     * */
    public String status;
    /**
     * set only if status InProgress
     * */
    public Long timeToFinish;
    public List<MenuItem> list = new ArrayList<MenuItem>();
}
