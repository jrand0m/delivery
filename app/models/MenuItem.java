package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class MenuItem extends Model {
    public boolean       avaliable = false;
    @ManyToOne
    public Client        client;
    public boolean       deleted   = false;
    public String        description;
    @Column(name="menuItemGroupId")
    public MenuItemGroup group;

    public Date          menuItemCreated;

    public String        name;
    /**
     * value in coins
     * */

    public Integer       price;

    public MenuItem() {

    }

    public MenuItem(Long id, String name, String description, Integer price,
            Boolean avaliable, Date menuItemCreated, MenuItemGroup group,
            Client client, Boolean deleted) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.avaliable = avaliable;
        this.menuItemCreated = menuItemCreated;
        this.group = group;
        this.client = client;
        this.deleted = deleted;
    }

    /**
     * Cloning constructor generates new id
     * */
    public MenuItem(MenuItem item) {
        this(null, item.name, item.description, item.price, item.avaliable,
                item.menuItemCreated, item.group, item.client, item.deleted);
    }

}
