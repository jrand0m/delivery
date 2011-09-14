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
public class Job {
    public String id;
    public String status;
    public List<MenuItem> list = new ArrayList<MenuItem>();
}
