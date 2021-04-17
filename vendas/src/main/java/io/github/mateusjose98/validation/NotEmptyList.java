package io.github.mateusjose98.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import io.github.mateusjose98.validation.constraintvalidation.NotEmptyListValidation;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidation.class)
public @interface NotEmptyList {
	
	String message() default "A lista não pode ser vazia nem nula!";
	Class<?> [] groups() default {};
	Class<? extends Payload> [] payload() default {};

}
