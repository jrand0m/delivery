package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = false")
public class AccountingTransaction extends Model {
    public static enum AC_TRANSACTION_STATE {

        APPROVED, PAID, RECIEVED, WAITING;

    }
    public Integer              amount;
    public boolean              deleted             = false;
    public String               desc;
    public AccountingGroup      group;
    @Id
    public Long                 id;
    public Date                 operationDate;
    public Integer              regularDayInMonthNo = -1;
    public AC_TRANSACTION_STATE state;

    /**
     * 
     * */
    public User                 target;

}
