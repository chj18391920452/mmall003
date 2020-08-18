package com.chj.configuration;

import com.chj.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new UserFilter());
        filterRegistrationBean.addUrlPatterns(
                "/cart/*",
                "/orders/*",
                "/userInfo.html",
                "/user/userInfo",
                "/orders/ordersList");
        return filterRegistrationBean;
    }
}
