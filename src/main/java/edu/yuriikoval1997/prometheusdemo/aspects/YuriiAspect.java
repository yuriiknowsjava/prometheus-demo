package edu.yuriikoval1997.prometheusdemo.aspects;

import edu.yuriikoval1997.prometheusdemo.controllers.HelloController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@Aspect
public class YuriiAspect {

    @Pointcut("@annotation(yuriiCounted)")
    public void yuriiPointcut(YuriiCounted yuriiCounted) {
        // pointcut declaration
    }

    @Pointcut("@annotation(exceptionHandler)")
    public void onExceptionHandler(ExceptionHandler exceptionHandler) {
        // pointcut declaration
    }

//    @Before(value = "yuriiPointcut(yuriiCounted)", argNames = "jp,yuriiCounted")
//    public void yuriiBefore(JoinPoint jp, YuriiCounted yuriiCounted) {
//        ((HelloController) jp.getThis())
//                .getHelloWorldCounter()
//                .labels(yuriiCounted.labels())
//                .inc();
//    }

    /**
     * According to Spring AspectJ documentation the advice's first argument must be ProceedingJoinPoint.
     * If it's missing, you will get exactly this exception message.
     * Sadly, the exception does not point to advice in error so solving the bug is a hit-and-miss.
     */
    @AfterReturning(value = "yuriiPointcut(yuriiCounted)", returning = "body", argNames = "jp,yuriiCounted,body")
    public void yuriiAfterReturning(JoinPoint jp, YuriiCounted yuriiCounted, Object body) {
        ResponseEntity<?> resp = (ResponseEntity<?>) body;

        String[] labels = new String[yuriiCounted.labels().length + 1];
        System.arraycopy(yuriiCounted.labels(), 0, labels, 0, yuriiCounted.labels().length);
        labels[labels.length-1] = Integer.toString(resp.getStatusCodeValue());

        ((HelloController) jp.getThis())
                .getHelloWorldCounter()
                .labels(labels)
                .inc();
    }

//    @AfterReturning(
//            value = "onExceptionHandler(exceptionHandler) && args(e,req)",
//            returning = "body",
//            argNames = "jp,exceptionHandler,body,e,req")
//    public void afterExceptionHandler(JoinPoint jp, ExceptionHandler exceptionHandler, Object body, Throwable e, WebRequest req) {
//        System.out.println(e.getStackTrace()[0].getClassName());
//    }

    @AfterThrowing(value = "yuriiPointcut(yuriiCounted)", throwing = "e", argNames = "jp,yuriiCounted,e")
    public void yuriiAfterThrowing(JoinPoint jp, YuriiCounted yuriiCounted, Throwable e) {
        String[] labels = new String[yuriiCounted.labels().length + 1];
        System.arraycopy(yuriiCounted.labels(), 0, labels, 0, yuriiCounted.labels().length);
        labels[labels.length-1] = e.getClass().getSimpleName();
        ((HelloController) jp.getThis())
                .getHelloWorldCounter()
                .labels(labels)
                .inc();
    }
}
