package helpers;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 1/28/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Crypto {
    public static String passwordHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot find SHA-1 alg!!!!",e);
        }
        return new String (Base64.encodeBase64(md.digest(password.getBytes())));
    }

}
