package helpers;

import play.Play;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 1/13/13
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cypher {
    public static String signWithApplicationKey(String somethingToSign){
        try {
            Mac cypher = Mac.getInstance("HmacSHA1");
            String key = Play.application().configuration().getString("application.secret");
            cypher.init(new SecretKeySpec(key.getBytes(),"HmacSHA1"));
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Problems with keys: Missing application key or something else", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problems with keys: Missing application key or something else",e);
        }
    }
}
