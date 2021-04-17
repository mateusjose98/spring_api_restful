package io.github.mateusjose98.validation.constraintvalidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.mateusjose98.validation.NotEmptyList;

public class NotEmptyListValidation implements ConstraintValidator<NotEmptyList, List>{

	@Override
	public boolean isValid(List value, ConstraintValidatorContext context) {
		
		return value != null && !value.isEmpty();
	}
	
	
	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
	}

}
