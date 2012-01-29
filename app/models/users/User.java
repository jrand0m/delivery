package models.users;

/**
 * User: Mike Stetsyshyn
 * Date: 1/28/12
 * Time: 6:57 PM
 */
public interface User {
    /**
     * DB id field.
     * null if id not set(entity not saved) 
     * */
    Long getId();
    
    Class<? extends User> getType();
    
    String getLogin();
    
    String getPassword();
}
