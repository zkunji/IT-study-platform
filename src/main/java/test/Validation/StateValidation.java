package test.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import test.anno.State;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     * @param s                          将来要校验的数据
     * @param constraintValidatorContext
     * @return 如果返回false校验不通过，true通过
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if (s == null) {
            return false;
        }
        return s.equals("已发布") || s.equals("审核中");
    }
}
