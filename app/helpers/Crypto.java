package helpers;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
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
    private static Charset UTF_8 = Charset.forName("UTF-8");
    public static String passwordHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot find SHA-1 alg!!!!",e);
        }
        byte[] passwordBytes = password.getBytes(UTF_8);
        byte[] digest = md.digest(passwordBytes);
        byte[] bytes = Base64.encodeBase64(digest);
        return new String (bytes);
    }

}
