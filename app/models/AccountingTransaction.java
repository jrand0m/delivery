package models;

import java.util.Date;

import siena.Id;
import siena.Model;

public class AccountingTransaction extends Model {
    @Id
    public Long id;
    public AC_TRANSACTION_STATE state;
    public Integer amount;
    public String desc;
    public Date operationDate;
    public boolean deleted = false;
    public AccountingGroup group;
    /**
     * 
     * */
    public User target;
    public Integer regularDayInMonthNo = -1;

    public static enum AC_TRANSACTION_STATE {

	WAITING, APPROVED, PAID, RECIEVED;

    }

}
