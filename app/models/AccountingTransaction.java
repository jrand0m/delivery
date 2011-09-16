package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import org.hibernate.annotations.Where;

import enumerations.AC_TRANSACTION_STATE;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class AccountingTransaction extends Model {

    public Integer              amount;
    public boolean              deleted             = false;
    public String               description;
    @ManyToOne
    public AccountingGroup      group;
    public Date                 operationDate;
    public Integer              regularDayInMonthNo = -1;
    public AC_TRANSACTION_STATE state;

    /**
     * 
     * */
    @ManyToOne
    public User                 target;

}
