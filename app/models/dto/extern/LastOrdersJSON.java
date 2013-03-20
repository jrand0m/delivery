package models.dto.extern;

import models.Order;

public class LastOrdersJSON {

    public LastOrdersJSON(Order ord) {
        s = ord.restaurant.getTwoLetters();
        f = ord.restaurant.getTitle();
        fid = ord.restaurant.getId();
        d = ord.oneLineDescription();
    }

    /**
     * Short letters of rest
     */
    public String s;
    /**
     * Full name of restaurant
     */
    public String f;
    /**
     * restaturant id
     */
    public Integer fid;
    /**
     * Short text of order
     */
    public String d;
}
