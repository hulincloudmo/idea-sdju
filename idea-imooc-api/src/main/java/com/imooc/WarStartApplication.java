package com.imooc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc
 * @ClassName: WarStartApplication
 * @Author: hulincloud
 * @Description: 继承spring boot
 * @Date: 2019/7/2 13:38
 * @Version: 1.0
 */
public class WarStartApplication extends SpringBootServletInitializer {
    /**
     *
     * @Description: 重写配置
     *
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
