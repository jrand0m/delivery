package controllers;

import helpers.TransactionGroupHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import controllers.Secure.Security;

import models.AccountingGroup;
import models.AccountingTransaction;
import models.AccountingTransaction.AC_TRANSACTION_STATE;
import models.User;
import play.Logger;
import play.data.binding.As;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.Model;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 * @author Mike Stetsyshyn
 * 
 *         class to make accounting and reporting on our income and outcome
 */
@With(Secure.class)
@Check("ADMIN")
public class Bookkeeper extends Controller {

    @Before
    static void _prepare() {
        User user = null;
        if (Security.isConnected()) {
            List<User> users = User.find("login = ?", Security.connected())
                    .first();
            if (users.size() != 0) {
                user = users.get(0);
                renderArgs.put(Application.USER_RENDER_KEY, user);
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
            AccountingGroup group = AccountingGroup.find("id = ?", groupId)
                    .first();
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

    public static void addTransaction(Integer amount, User target,
            String description, Long groupId) {
        AccountingTransaction tran = new AccountingTransaction();
        tran.amount = amount;
        tran.description = description;
        tran.deleted = false;
        tran.group = AccountingGroup.findById(groupId);
        tran.state = AC_TRANSACTION_STATE.WAITING;
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
