package com.example.identity_service.annotations.impl;

import com.example.identity_service.annotations.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;


public class AgeValidator implements ConstraintValidator<Age, LocalDate>
{


    private int age;

    // Initialize the validator with the 'value' defined in the custom annotation
    @Override
    public void initialize(Age ageAnnotation) {
        this.age = ageAnnotation.value();
    }

    // Implement the validation logic
    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) {
            return false; // Consider null invalid or customize this
        }

        // Calculate the age difference from the date of birth to today
        return Period.between(dob, LocalDate.now()).getYears() >= age;
    }
}
