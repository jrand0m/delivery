package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
public class IrregularDay extends Day {
    public boolean deleted = false;
}
