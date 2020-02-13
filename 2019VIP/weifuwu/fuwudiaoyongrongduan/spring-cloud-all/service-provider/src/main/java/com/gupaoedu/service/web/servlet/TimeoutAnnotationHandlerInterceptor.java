//package com.gupaoedu.service.web.servlet;
//
//import com.gupaoedu.service.annotation.Timeout;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//import java.util.concurrent.*;
//import java.util.stream.Stream;
//
///**
// * {@link Timeout @Timeout} 注解处理 Web MVC 拦截器
// *
// * @see HandlerInterceptor
// * @see Timeout
// */
//public class TimeoutAnnotationHandlerInterceptor implements HandlerInterceptor {
//
//    private ExecutorService executorService = Executors.newFixedThreadPool(2);
//
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 1. 拦截处理方法（Spring Web MVC 内建 HandlerInterceptor）
//        // 2. 得到被拦截的方法对象（handler 对象在 Spring Web MVC 注解编程中永远是 HandlerMethod）
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            // 2.1 通过 HandlerMethod 获取 Method 对象
//            Method method = handlerMethod.getMethod();
//            // 3. 通过 Method 获取标注的 @Timeout 注解
//            Timeout timeout = method.getAnnotation(Timeout.class);
//            if (timeout != null) { // 如果标注的话
//                // 4. 获取 @Timeout 注解中的属性
//                Object bean = handlerMethod.getBean();
//                Long value = timeout.value();
//                TimeUnit timeUnit = timeout.timeUnit();
//                String fallback = timeout.fallback();
//                // 5. 根据以上数据构造 Future 超时时间
//                Future<Object> future = executorService.submit(new Callable<Object>() {
//                    @Override
//                    public Object call() throws Exception {
//                        return method.invoke(bean);
//                    }
//                });
//                Object returnValue = null;
//                try {
//                    // 6. 执行被拦截的方法
//                    returnValue = future.get(value, timeUnit); // 正常处理
//                } catch (TimeoutException e) {
//                    // 7. 如果失败，调用 fallback 方法
//                    returnValue = invokeFallbackMethod(handlerMethod, bean, fallback);      // 补偿处理
//                }
//                // 8. 返回执行结果（当前实现是存在缺陷的，大家可以尝试通过 HandlerMethodReturnValueHandler 实现）
//                response.getWriter().write(String.valueOf(returnValue));
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    private Object invokeFallbackMethod(HandlerMethod handlerMethod, Object bean, String fallback) throws Exception {
//        // 7.1 查找 fallback 方法
//        Method method = findFallbackMethod(handlerMethod, bean, fallback);
//        return method.invoke(bean);
//    }
//
//    private Method findFallbackMethod(HandlerMethod handlerMethod, Object bean, String fallbackMethodName) throws NoSuchMethodException {
//        // 通过被拦截方法的参数类型列表结合方法名，从同一类中找到 fallback 方法
//        Class beanClass = bean.getClass();
//        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
//        Class[] parameterTypes = Stream.of(methodParameters)
//                .map(MethodParameter::getParameterType) // Class
//                .toArray(Class[]::new);                 // Stream<Class> -> Class[]
//        Method fallbackMethod = beanClass.getMethod(fallbackMethodName, parameterTypes);
//        return fallbackMethod;
//    }
//}
