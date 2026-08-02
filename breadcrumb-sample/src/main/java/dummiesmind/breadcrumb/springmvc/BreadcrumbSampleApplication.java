package dummiesmind.breadcrumb.springmvc;

import dummiesmind.breadcrumb.springmvc.config.BreadCrumbConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BreadCrumbConfiguration.class)
public class BreadcrumbSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreadcrumbSampleApplication.class, args);
    }
}
