package com.gupaoedu.service.AOP;


import com.gupaoedu.service.annotation.Must;
import com.gupaoedu.service.annotation.Validate;
import com.gupaoedu.service.entity.Param;
import com.gupaoedu.service.exception.AopException;
import com.gupaoedu.service.exception.BusinessException;
import com.gupaoedu.service.utils.AnnotationHelper;
import com.gupaoedu.service.utils.Check;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 链接：
 * 自定义校验
 * https://blog.csdn.net/u011710466/article/details/53909059
 */
@Aspect
@Component
@Order(7)
public class ParamValidateAspect {

    private final static Logger logger = LoggerFactory.getLogger(ParamValidateAspect.class);



    @Around("@annotation(com.gupaoedu.service.annotation.Validate)")
    public Object aroundLimitedMethodInvocation(ProceedingJoinPoint point) throws Throwable {
        logger.info("开始拦截接口入参!");
        // eg：objs 的值 http://localhost:8083/test?name=guoyiguang&age=12  可以获得 12，guoyiguang
        Object[] objs = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //检测
        Annotation[][] annos = method.getParameterAnnotations();
        Parameter[] paraArray = method.getParameters();
        // 只要有一个参数注解 被Annotaion 标记（annotation.）就返回true:
        // a.annotationType() == Validate.class
        // Method 的 getAnnotation(注解的Class) 这个是针对方法的，不是方法参数
        boolean flag = validateParameterAnnotation(annos);
        //虽然方法加了注解,但是参数么有注解,pass
        if (!flag) {
            return point.proceed(objs);
        }

        //得到标注@Validate注解的参数
        List<Param> params = AnnotationHelper.getParms(method, objs);
        if (!Check.NuNList(params)) {
            for (Param param : params) {
                String validRes = validateDetail(param);
                if (!Check.NuNString(validRes)) {
                    logger.info("客户端上报参数错误详细信息:{}", validRes);
                    //return ResponseUtil.message(ErrorCode.CLIENT_ERROR.getCode(), "客户端上报参数错误");
                    throw new AopException("406", validRes);
                }

            }

        }
        //没有错误就沿着毛主席的路线继续前进!
        return point.proceed(objs);
    }


    private String validateDetail(Param param) throws IllegalArgumentException, IllegalAccessException {

        Validate val = (Validate) param.getAnno();
        boolean isVali = val.isValidate();

        StringBuilder sb = new StringBuilder();
        if (isVali) {
            if (val.isForm() == true) {
                String res = validateForm(param);
                append(sb, res);
            } else {
                String res = validateCommon(param);
                append(sb, res);
            }
        }
        return sb.toString();
    }


    private void append(StringBuilder sb, String res) {

        if (!Check.NuNString(res)) {
            sb.append("_");
            sb.append(res);
        }
    }


    /**
     * 验证是否有某个注解
     *
     * @param annos
     * @param annos
     * @return
     */


    private boolean validateParameterAnnotation(Annotation[][] annos) {

        boolean flag = false;
        for (Annotation[] at : annos) {
            for (Annotation a : at) {
                if (a.annotationType() == Validate.class) {
                    flag = true;
                }
            }
        }
        return flag;
    }


    private String validateCommon(Param param) {
        String res = null;
        if (Check.NuNObject(param.getValue())) {

            res = param.getName() + "的参数值为空!";
        }
        return res;
    }

    private String validateForm(Param param) throws IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = param.getValue().getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field f : fields) {
            //
            Annotation[] annos = f.getAnnotations();
            //f.isAnnotationPresent(Must.class);


            if (!Check.NuNArray(annos)) {
                String paramName = param.getName() + "." + f.getName();
                Must must = (Must) annos[0];
                // 自定义注解 public boolean isMust() default true; 默认是true
                if (must.isMust()) {
                    f.setAccessible(true);
                    Object obj = f.get(param.getValue());
                    Class<?> type = f.getType();
                    if (type.isArray()) {
                        Object[] arr = (Object[]) obj;
                        if (Check.NuNArray(arr)) {
                            append(sb, paramName + "不能为空!");
                        }
                    } else if (type.isPrimitive()) {
                        if (type == int.class) {
                            int intObj = (int) obj;
                            if (intObj <= 0) {
                                append(sb, paramName + "不能小于等于0!");
                            }
                        } else if (type == long.class) {
                            long longObj = (long) obj;
                            if (longObj <= 0) {
                                append(sb, paramName + "不能小于等于0!");

                            }
                        }

                    } else if (type == String.class) {
                        if (Check.NuNString((String) obj)) {
                            append(sb, paramName + "不能为空!");

                        }
                    }

                }

            }

        }

        return sb.toString();

    }

}
