package com.example.seckill.vo;

import com.example.seckill.utils.MobileValidationUtil;
import com.example.seckill.validation.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidation implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return MobileValidationUtil.isMobile(s);
        }
        else{
            if(StringUtils.isEmpty(s)){
                return true;
            }
            else {
                return MobileValidationUtil.isMobile(s);
            }
        }

    }
}
