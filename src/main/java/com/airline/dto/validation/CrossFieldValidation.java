package com.airline.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CrossFiledValidator.class)
@Documented
public @interface CrossFieldValidation
{
    String message() default "Only one field must be filled in - or dates, or period";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}