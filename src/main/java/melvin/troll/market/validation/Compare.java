package melvin.troll.market.validation;

import javax.lang.model.element.Element;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CompareValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Compare {

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
    public String message();
    public String firstField();
    public String secondField();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Compare[] value();
    }
}
