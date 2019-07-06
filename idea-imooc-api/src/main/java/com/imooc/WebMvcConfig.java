package com.imooc;

import com.imooc.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author hulincloud
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                                    .addResourceLocations("file:D:\\SDJU_research_userData/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Bean
    public MiniInterceptor miniInterceptor(){
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(miniInterceptor())
//                .addPathPatterns("/bgm/list")
                ;
    }
}
