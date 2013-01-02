package enumerations;

/**
 * Used to distinguish type of log messages Change for changing of values Dump
 * for dump internal DB or other system info (used in devices only) JOB for
 * regular job Info for miscellaneous info PING special type for device pings
 * */
public enum LogActionType {
	CHANGE, DUMP, JOB, INFO, PING
}
