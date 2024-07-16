package test.exception;

import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import test.result.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        return Result.fail(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");

    }
}
