/**
 * 
 */
package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use Carefully!
 * Allow to use method from secured class by unlogined user
 * 
 * @author Mike
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AllowAnonymous {
    String[] value();
}
