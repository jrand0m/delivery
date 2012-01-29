package services;

import models.Order;

/**
 * User: Mike Stetsyshyn
 * Date: 1/29/12
 * Time: 12:21 PM
 */
public interface MailService {
    void sendNewOrderNotification(Order o);
}
