package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@DiscriminatorValue("IRREGULAR_DAY")
public class IrregularDay extends Day {
    public static final class FIELDS{
	public static final String IRREGULARDAY_DELETED = "deleted";
    }
    public boolean deleted = false;
}
