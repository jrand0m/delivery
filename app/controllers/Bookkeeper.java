package controllers;

import helpers.TransactionGroupHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.AccountingGroup;
import models.AccountingTransaction;
import models.AccountingTransaction.AC_TRANSACTION_STATE;
import models.User;
import play.data.binding.As;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import siena.Model;

/**
 * @author Mike Stetsyshyn
 * 
 *         class to make accounting and reporting on our income and outcome
 */
@With(Secure.class)
@Check("Accounting")
public class Bookkeeper extends Controller {

    @Before
    static void _prepare() {
	// todo login check??
    }

    public static void index() {
	showReport(null, null, null, true);
    }

    public static void showReport(Long groupId, @As("dd/MM/yyyy") Date from,
	    @As("dd/MM/yyyy") Date to, boolean excluding) {
	ArrayList<TransactionGroupHolder> transactions = new ArrayList<TransactionGroupHolder>();
	if (groupId == null || groupId < 0) {
	    List<AccountingGroup> groups = Model.all(AccountingGroup.class)
		    .filter("deleted", false).fetch();
	    for (AccountingGroup group : groups) {
		TransactionGroupHolder tgh = new TransactionGroupHolder();
		tgh.setGroup(group);

		List<AccountingTransaction> transacts = Model
			.all(AccountingTransaction.class)
			.filter("deleted", false).filter("group", group)
			.fetch();
		// TODO get by date
		tgh.setTransactions(transacts);
		transactions.add(tgh);
	    }
	} else {
	    AccountingGroup group = Model.all(AccountingGroup.class)
		    .filter("deleted", false).filter("id", groupId).get();
	    TransactionGroupHolder tgh = new TransactionGroupHolder();
	    tgh.setGroup(group);

	    List<AccountingTransaction> transacts = Model
		    .all(AccountingTransaction.class).filter("deleted", false)
		    .filter("group", group).fetch();
	    // TODO get by date
	    tgh.setTransactions(transacts);
	    transactions.add(tgh);
	}
	render(transactions);
    }

    public static void addTransaction(Integer amount, User target,
	    String description, Long groupId) {
	AccountingTransaction tran = new AccountingTransaction();
	tran.amount = amount;
	tran.desc = description;
	tran.deleted = false;
	tran.group = Model.getByKey(AccountingGroup.class, groupId);
	tran.state = AC_TRANSACTION_STATE.WAITING;
	tran.operationDate = new Date();
	tran.insert();
	todo();
    }

    public static void modTransaction(Long id) {

	todo();
    }

    public static void delTransaction(Long id) {

	todo();
    }
}
