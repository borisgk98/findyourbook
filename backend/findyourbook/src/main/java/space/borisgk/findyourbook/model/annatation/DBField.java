package space.borisgk.findyourbook.model.annatation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DBField {
    String field() default "";
    /**
    PRIMARY KEY constraint. Field, what marked by this annotation should be Integer. For example Id
     */
    String constraint() default "";
}
