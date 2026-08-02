package dummiesmind.breadcrumb.springmvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import dummiesmind.breadcrumb.springmvc.interceptor.BreadCrumbInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import(BreadcrumbSampleApplication.class)
class BreadcrumbSampleApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsTheFullTrailForADirectDeepLink() throws Exception {
        mockMvc.perform(get("/first/second/third"))
                .andExpect(status().isOk())
                .andExpect(view().name("third"))
                .andExpect(model().attribute(BreadCrumbInterceptor.MODEL_ATTRIBUTE, hasSize(3)))
                .andExpect(content().string(containsString("<span>Third level</span>")));
    }
}
