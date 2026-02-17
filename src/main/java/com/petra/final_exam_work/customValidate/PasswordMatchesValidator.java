package com.petra.final_exam_work.customValidate;

import com.petra.final_exam_work.dto.requestDto.ContributorSignUpRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, ContributorSignUpRequest> {

    @Override
    public boolean isValid(ContributorSignUpRequest request,
                           ConstraintValidatorContext context) {

        if (request.getPassword() == null || request.getConfirmPassword() == null) {
            return true;
        }

        return request.getPassword().equals(request.getConfirmPassword());
    }
}
