package models;

import java.util.Date;
import java.util.List;

import siena.Id;
import siena.Model;

public abstract class LoggableModel extends Model {
	protected abstract List<LoggableModel> getLoggableChildren();

	protected abstract void archive();

	@Id
	public Long id;

	public User modifiedBy;
	public Date modifiedOn;

	public LOGGED_ACTION action = LOGGED_ACTION.CREATED;

	public enum LOGGED_ACTION {
		CREATED, MODIFIED, DELETED, ARCHIVED
	}

	public void softDelete() {
		for (LoggableModel child : getLoggableChildren()) {
			child.archive();
		}
	}

}
