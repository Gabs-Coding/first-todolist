package code.gabs.todolist.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<FilterTaskAuth> filterTaskAuthRegistration(FilterTaskAuth filterTaskAuth) {
        FilterRegistrationBean<FilterTaskAuth> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filterTaskAuth);
        registrationBean.addUrlPatterns("/api/task/*");
        return registrationBean;
    }
}
