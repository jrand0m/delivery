package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;
import enumerations.TransactionState;

@Entity
@Where(clause = "deleted = 0")
public class AccountingTransaction extends Model {
    public final static class FIELDS{
	public final static String AMOUNT = "amount";
	public final static String DELETED = "deleted";
	public final static String DESCRIPTION = "description";
	public final static String GROUP = "group";
	public final static String OPERATIONDATE = "operationDate";
	public final static String REGULARDAYINMONTHNO = "regularDayInMonthNo";
	public final static String STATE = "state";
	public final static String TARGET = "target";
    }
    public Integer              amount;
    public boolean              deleted             = false;
    public String               description;
    @ManyToOne
    public AccountingGroup      group;
    public Date                 operationDate;
    public Integer              regularDayInMonthNo = -1;
    public TransactionState state;

    /**
     * 
     * */
    @ManyToOne
    public User                 target;

}
