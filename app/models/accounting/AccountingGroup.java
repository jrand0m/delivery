package models.accounting;

import siena.Id;
import siena.Model;

public class AccountingGroup extends Model {
    @Id
    public Long id;
    public String name;
    public String desc;
    public Boolean deleted = Boolean.FALSE;

}
