package com.rawat.electrolok.store.Validate;

import jakarta.validation.Constraint;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    // Default Error Message
    java.lang.String message() default "{Invalid Image Name.....}";
    // Represents Group of Constraints
    java.lang.Class<?>[] groups() default {};
    // Additional Information about the annotation
    java.lang.Class<? extends jakarta.validation.Payload>[] payload() default {};
}
