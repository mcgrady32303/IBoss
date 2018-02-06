package com.app.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.app")
@EntityScan("com.app.entity")
@EnableJpaRepositories("com.app.repository")
public class WebAppConfig extends WebMvcConfigurerAdapter {
//	@Autowired
//	private Crawler crawler;
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebAppConfig.class);
	}
	
    public static void main(String[] args) {
		SpringApplication.run(WebAppConfig.class, args);
	} 
    
    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/repository/**");
    	registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/advance/**");
	}

    
    /**
     * spring boot 定时任务
     */
//    @Scheduled(cron="0 0 22 * * ?")
//    public void reportCurrentTime() {
//    	crawler.getBlogList(1);
//    }
}