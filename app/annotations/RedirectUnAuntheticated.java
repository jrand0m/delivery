/**
 *
 */
package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mike Redirection to page if user is not eligible to use this method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RedirectUnAuntheticated {
    /**
     * route to redirect if !isCOnnected()
     */
    String route();

    /**
     * ???
     */
    String originUrl();
}
