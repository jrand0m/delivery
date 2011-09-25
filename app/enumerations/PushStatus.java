package enumerations;

public enum PushStatus {
	INPROGRESS, READY, TAKEN, REJECTED;
	public static PushStatus convert(String status){
		if (INPROGRESS.toString().equalsIgnoreCase(status)){
			return INPROGRESS;
		}else if (READY.toString().equalsIgnoreCase(status)){
			return READY;
		}else if (TAKEN.toString().equalsIgnoreCase(status)){
			return TAKEN;
		}else if (REJECTED.toString().equalsIgnoreCase(status)){
			return REJECTED;
		}
		return null;
	}
}
