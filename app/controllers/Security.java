package controllers;

/**
 * Overriden security model
 * 
 * @see http://www.playframework.org/documentation/1.2.2/guide8
 * @see http://www.playframework.org/community/snippets/2
 * @author mike
 * */

public class Security extends Secure.Security {

    static boolean authentify(String username, String password) {

	// TODO [Mike] Make normal security model based on users!
	return true;
    }

}
