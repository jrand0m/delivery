package models;

import siena.Id;
import siena.Model;

public class WorkHours extends Model {
    @Id
    public Long id;
    public Client client;
    public String description;
}
