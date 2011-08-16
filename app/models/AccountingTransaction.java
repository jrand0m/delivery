package models;

import java.util.Date;

import siena.Id;
import siena.Model;

public class AccountingTransaction extends Model {
    @Id
    public Long id;
    public AC_TRANSACTION_TYPE type;
    public Integer amount;
    public String desc;
    public Date operationDate;
    public Boolean deleted = Boolean.FALSE;
    public AccountingGroup group;

    public static enum AC_TRANSACTION_TYPE {
	/**
	 * ti sho v podatkove mozhna nesty
	 * */
	OFFICIAL,
	/**
	 * ti sho ne vhodjat' v podatkovi documenty (neoficiini)
	 * */
	INTERNAL

    }

}
