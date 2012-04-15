package services.ebean;

import models.Order;
import services.MailService;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 3/17/12
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailServiceEbeanImpl implements MailService{
    @Override
    public void sendNewOrderNotification(Order o) {
        throw new UnsupportedOperationException("Implement Me");
    }
}
