package models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@DiscriminatorValue("IRREGULAR_DAY")
public class IrregularDay extends Day {
    public boolean deleted = false;
}
