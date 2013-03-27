package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vd_menu_items_groups")
@SequenceGenerator(name = "menu_items_groups_seq_gen", sequenceName = "menu_items_groups_seq")
public class MenuItemGroup extends Model {
    @Id
    @GeneratedValue(generator = "menu_items_groups_seq_gen", strategy = GenerationType.SEQUENCE)
    public Integer id;
    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;
    @Column(name = "deleted", nullable = false)
    public boolean deleted = false;

    @Column(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    public Integer restaurant_Id;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @Deprecated
    public Restaurant restaurant;

    public String anchorName() {
        return "g" + hashCode();
    }

}