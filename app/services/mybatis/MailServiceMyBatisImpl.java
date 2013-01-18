package services.mybatis;

import models.Order;
import services.MailService;

/**
 * User: Mike Stetsyshyn
 */
public class MailServiceMyBatisImpl implements MailService {
    @Override
    public void sendNewOrderNotification(Order o) {
        throw new UnsupportedOperationException();
    }
}
