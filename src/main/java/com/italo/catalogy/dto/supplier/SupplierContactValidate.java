package com.italo.catalogy.dto.supplier;

import com.italo.catalogy.model.enums.ContactSupplierType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SupplierContactValidate implements ConstraintValidator<SupplierContact, CreateSupplierRequestDTO> {
    @Override
    public boolean isValid(CreateSupplierRequestDTO createSupplierRequestDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (createSupplierRequestDTO.contactSuplierType()== ContactSupplierType.EMAIL){
            return createSupplierRequestDTO.contactValue().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        } else if (createSupplierRequestDTO.contactSuplierType()== ContactSupplierType.PHONE) {
            return createSupplierRequestDTO.contactValue().matches("^\\d{11}$");
        }
        else return false;
    }
}
