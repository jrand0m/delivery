package controllers;

import helpers.TransactionGroupHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import models.AccountingGroup;
import models.AccountingTransaction;
import models.users.EndUser;
import models.users.SystemAdministrator;
import play.data.binding.As;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import annotations.Check;

/**
 * class to make accounting and reporting on our income and outcome
 * 
 * @author Mike Stetsyshyn
 */
@With(Secure.class)
@Check(SystemAdministrator.class)
public class Bookkeeper extends Controller {
	public static final class RENDER_KEYS{
		public static final String USER = "user";
	}
	@Before
	static void _prepare() {
		EndUser user = null;
		if (Security.isConnected()) {
			List<EndUser> users = EndUser.find(EndUser.HQL.BY_LOGIN,
					Security.connected()).first();
			if (users.size() != 0) {
				user = users.get(0);
				renderArgs.put(RENDER_KEYS.USER, user);
			}
		} else {
			notFound();
		}
	}

	public static void index() {
		showReport(null, null, null);
	}

	public static void showReport(Long groupId, @As("dd/MM/yyyy") Date from,
			@As("dd/MM/yyyy") Date to) {
		ArrayList<TransactionGroupHolder> transactions = new ArrayList<TransactionGroupHolder>();
		if (groupId == null || groupId < 0) {
			List<AccountingGroup> groups = AccountingGroup.findAll();
			for (AccountingGroup group : groups) {
				TransactionGroupHolder tgh = new TransactionGroupHolder();
				tgh.setGroup(group);
				List<AccountingTransaction> transacts = listTransacts(group,
						from, to);
				tgh.setTransactions(transacts);
				transactions.add(tgh);
			}
		} else {
			AccountingGroup group = AccountingGroup.findById(groupId);
			TransactionGroupHolder tgh = new TransactionGroupHolder();
			tgh.setGroup(group);
			List<AccountingTransaction> transacts = listTransacts(group, from,
					to);
			tgh.setTransactions(transacts);
			transactions.add(tgh);
		}
		render(transactions);

	}

	/**
	 * @param group
	 * @param from
	 * @param to
	 * @return
	 */
	private static List<AccountingTransaction> listTransacts(
			AccountingGroup group, Date from, Date to) {
		TypedQuery<AccountingTransaction> query = null;
		String queryStr = "group = ?";
		if (from != null) {
			queryStr += " and operationDate > ?";
		}
		if (to != null) {
			queryStr += " and operationDate < ?";
		}
		query = AccountingTransaction.em().createQuery(queryStr,
				AccountingTransaction.class);
		int pos = 1;
		query.setParameter(pos, group);

		if (from != null) {
			query.setParameter(++pos, from);
		}
		if (to != null) {
			query.setParameter(++pos, to);
		}

		return query.getResultList();
	}

	public static void addTransaction(Integer amount, EndUser target,
			String description, Long groupId) {
		AccountingTransaction tran = new AccountingTransaction();
		tran.amount = amount;
		tran.description = description;
		tran.deleted = false;
		tran.group = AccountingGroup.findById(groupId);
		tran.state = enumerations.TransactionState.WAITING;
		tran.operationDate = new Date();
		tran.create();
		todo();
	}

	public static void modTransaction(Long id) {
		todo();
	}

	public static void delTransaction(Long id) {

		AccountingTransaction transaction = AccountingTransaction.findById(id);
		transaction.deleted = false;
		transaction.save();
		// TODO Mike: Add messages...
		ok();

	}
}
