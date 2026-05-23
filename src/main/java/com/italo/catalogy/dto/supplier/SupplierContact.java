package com.italo.catalogy.dto.supplier;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SupplierContactValidate.class)
public @interface SupplierContact {
    String message() default "O contato digitado não condiz com o tipo escolhido";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
