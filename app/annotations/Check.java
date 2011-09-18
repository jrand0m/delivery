package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import enumerations.UserRoles;

/**
 * Annotation for security Check roles in value for current logged in user
 * 
 * @author mike
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Check {

    /**
     * roles to check (array of strings eg.
     * 
     * <pre>
     * @Check(value = { "ADMIN", "RESTAURANT", "OTHR" })
     * </pre>
     * 
     * )
     * */
    UserRoles[] value();

}
