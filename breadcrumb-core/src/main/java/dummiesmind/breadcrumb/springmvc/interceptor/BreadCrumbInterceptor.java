package dummiesmind.breadcrumb.springmvc.interceptor;

import dummiesmind.breadcrumb.springmvc.annotations.Link;
import dummiesmind.breadcrumb.springmvc.breadcrumb.BreadCrumbLink;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BreadCrumbInterceptor implements HandlerInterceptor {

    public static final String MODEL_ATTRIBUTE = "breadcrumbs";

    private final BreadCrumbRegistry registry;

    public BreadCrumbInterceptor(BreadCrumbRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {
        if (modelAndView == null) {
            return;
        }
        if (handler instanceof HandlerMethod handlerMethod) {
            Link link = handlerMethod.getMethodAnnotation(Link.class);
            if (link != null) {
                List<BreadCrumbLink> breadcrumbs = registry.resolve(link, request.getContextPath());
                modelAndView.addObject(MODEL_ATTRIBUTE, breadcrumbs);
            }
        }
    }
}
