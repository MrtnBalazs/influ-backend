package mrtn.influ.auth.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class ControllerLoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(mrtn.influ.auth.log.LogRequestResponse)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();

        Object[] args = joinPoint.getArgs();
        String argsString = Arrays.stream(args)
                .map(arg -> arg != null ? arg.toString() : "null")
                .collect(Collectors.joining(", "));

        log.info("➡️ Entering: {}({})", methodName, argsString);

        Object result = joinPoint.proceed();

        log.info("⬅️ Exiting: {} => {}", methodName, result);

        return result;
    }
}
