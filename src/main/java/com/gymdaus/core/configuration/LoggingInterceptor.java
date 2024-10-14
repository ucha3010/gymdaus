package com.gymdaus.core.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            RequestMapping classMapping = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), RequestMapping.class);
            String[] controller = new String[1];
            String[] method = new String[1];
            String verb = "";
            if (classMapping != null) {
                controller = classMapping.value();
            }

            if (handlerMethod.hasMethodAnnotation(GetMapping.class)) {
                verb = "GET";
                GetMapping getMapping = handlerMethod.getMethodAnnotation(GetMapping.class);
                method = getMapping.value();
            }
            if (handlerMethod.hasMethodAnnotation(PostMapping.class)) {
                verb = "POST";
                PostMapping postMapping = handlerMethod.getMethodAnnotation(PostMapping.class);
                method = postMapping.value();
            }
            logger.info("URI request: {} - {}{}", verb, controller[0], method[0]);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // Este método se llama después de que se ha manejado la solicitud, puedes agregar lógica adicional si es necesario.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Este método se llama después de que se ha completado la solicitud, puedes agregar lógica adicional si es necesario.
    }
}
