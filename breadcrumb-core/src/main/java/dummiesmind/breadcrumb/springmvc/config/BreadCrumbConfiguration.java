package dummiesmind.breadcrumb.springmvc.config;

import dummiesmind.breadcrumb.springmvc.interceptor.BreadCrumbInterceptor;
import dummiesmind.breadcrumb.springmvc.interceptor.BreadCrumbRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class BreadCrumbConfiguration {

    @Bean
    BreadCrumbRegistry breadCrumbRegistry(ObjectProvider<RequestMappingHandlerMapping> handlerMappingProvider) {
        return new BreadCrumbRegistry(handlerMappingProvider);
    }

    @Bean
    BreadCrumbInterceptor breadCrumbInterceptor(BreadCrumbRegistry registry) {
        return new BreadCrumbInterceptor(registry);
    }

    @Bean
    WebMvcConfigurer breadCrumbWebMvcConfigurer(BreadCrumbInterceptor interceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(interceptor);
            }
        };
    }
}
