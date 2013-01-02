package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import play.db.jpa.Model;

@Entity
//@Where(clause = "deleted = 0")
public class MenuItemGroup extends Model {
	public static final class FIELDS {
		public static final String MENU_ITEM_GROUP_DELETED = "deleted";
		public static final String MENU_ITEM_GROUP_DESCRIPTION = "description";
		public static final String MENU_ITEM_GROUP_NAME = "name";
		public static final String RESTAURANT = "restaurant";

	}

	public boolean deleted = false;
	@ManyToOne
	public Restaurant restaurant;
	@OneToMany(mappedBy = MenuItem.FIELDS.MENU_ITEM_GROUP)
    @Where(clause = "deleted = 'f' ")
	public List<MenuItem> items;
	public String description;
	public String name;

	public String anchorName() {
		return "g" + hashCode();
	}
	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append('[');
		if (items != null)
		for (MenuItem itm : items){
			b.append(itm.toString());
			b.append(',');
		}
		b.append(']');
		return name + " " + description + " " + b.toString() ;
	}
=======


@Table(name = "vd_menu_items_groups")
@SequenceGenerator(name = "menu_items_groups_seq_gen", sequenceName = "menu_items_groups_seq")
public class MenuItemGroup {
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

    //EXCEPTION! needed for backward compatibility with templates....
    public List<MenuItem> items = new ArrayList<MenuItem>();

    public String anchorName() {
        return "g" + hashCode();
    }

>>>>>>> master
}