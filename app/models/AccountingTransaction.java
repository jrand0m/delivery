package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import models.users.EndUser;
import models.users.User;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;
import enumerations.TransactionState;

@Entity
//@Where(clause = "deleted = 0")
public class AccountingTransaction extends Model {
	public final static class FIELDS {
		public final static String AT_AMOUNT = "amount";
		public final static String AT_DELETED = "deleted";
		public final static String AT_DESCRIPTION = "description";
		public final static String AT_GROUP = "group";
		public final static String AT_OPERATION_DATE = "operationDate";
		public final static String AT_REGULAR_DAY_IN_MONTH_NO = "regularDayInMonthNo";
		public final static String AT_STATE = "state";
		public final static String AT_TARGET = "target";
	}

	public Integer amount;
	public boolean deleted = false;
	public String description;
	@ManyToOne
	public AccountingGroup group;
	public Date operationDate;
	public Integer regularDayInMonthNo = -1;
	@Enumerated(value = EnumType.STRING)
	public TransactionState state;

	/**
     * 
     * */
	@ManyToOne
	public User target;

}
