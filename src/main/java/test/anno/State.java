package test.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import test.Validation.StateValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {StateValidation.class})//指定提供校验规则的类
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface State {
    //提供校验失败后的提示信息
    String message() default "state参数的值只能是已发布或者草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到state注解的附加信息
    Class<? extends Payload>[] payload() default {};

}
