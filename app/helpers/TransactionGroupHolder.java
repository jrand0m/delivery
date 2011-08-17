package helpers;

import java.util.List;

import models.AccountingGroup;
import models.AccountingTransaction;

public class TransactionGroupHolder {
    private AccountingGroup group;
    private List<AccountingTransaction> transactions;

    public void setGroup(AccountingGroup group) {
	this.group = group;

    }

    public AccountingGroup getGroup() {
	return this.group;

    }

    public List<AccountingTransaction> getTransactions() {
	return transactions;
    }

    public void setTransactions(List<AccountingTransaction> transactions) {
	this.transactions = transactions;
    }

}
