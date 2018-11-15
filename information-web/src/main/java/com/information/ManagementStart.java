package com.information;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import com.information.interceptor.UserVerificationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.github.pagehelper.PageHelper;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = "com.information")
// @SpringBootApplication是Spring Boot的核心注解，它是一个组合注解:	exclude里面的两个类：禁用springboot自带的对mongodbd的自动配置
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
@MapperScan(basePackages = { "com.information.dao"})	// mybatis的mapper包的扫描
@EnableSwagger2 //启动swagger注解
@EnableScheduling
public class ManagementStart extends WebMvcConfigurerAdapter{

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ManagementStart.class, args);
//		initDispatchUrls();
	}
	
	 /**  
     * 文件上传配置大小
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("10240KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  
	
	/**
	 * 拦截器配置
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new UserVerificationInterceptor()).addPathPatterns("/**").excludePathPatterns("/dev/**","/admin/**","/h5/**");
       
        super.addInterceptors(registry);
    }
	
	// 通过@Bean定义HttpMessageConverter是向项目中添加消息转换器最简便的办法
	@Bean // @Bean表示这是一个Spring管理的bean，@SpringBootApplication组合注解里面有一个@Configuration 申明这是一个配置类相当于xml配置文件
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override	// 扩展现有的消息转换器链表
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {  
        configurer.favorPathExtension(false);
    } 
	

	// @Override
	// protected SpringApplicationBuilder configure(SpringApplicationBuilder
	// application) {
	// return application.sources(ManagementStart.class);
	// }

//	 @Bean
//	 public EmbeddedServletContainerFactory servletContainer() {
//		 TomcatEmbeddedServletContainerFactory tomcat = new
//		 TomcatEmbeddedServletContainerFactory() {
//			 @Override
//			 protected void postProcessContext(Context context) {
//			 SecurityConstraint constraint = new SecurityConstraint();
//			 constraint.setUserConstraint("CONFIDENTIAL");
//			 SecurityCollection collection = new SecurityCollection();
//			 collection.addPattern("/*");
//			 constraint.addCollection(collection);
//			 context.addConstraint(constraint);
//			 }
//		 };
//		 tomcat.addAdditionalTomcatConnectors(httpConnector());
//		 return tomcat;
//	 }
//	
//	 @Bean
//	 public Connector httpConnector() {
//		 Connector connector = new
//		 Connector("org.apache.coyote.http11.Http11NioProtocol");
//		 connector.setScheme("http");
//		 //Connector监听的http的端口号
//		 connector.setPort(80);
//		 connector.setSecure(false);
//		 //监听到http的端口号后转向到的https的端口号
//		 connector.setRedirectPort(8016);
//		 return connector;
//	 }

	// 配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}

//	public static void initDispatchUrls(){
//		UserVerificationInterceptor.dispathcUrls.put("/", "/");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/login", "/management/v1/login");
//		UserVerificationInterceptor.dispathcUrls.put("/error", "/error");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/readImage", "/management/v1/readImage");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/checkAgentAccount", "/management/v1/checkAgentAccount");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/checkSublessorAccount", "/management/v1/checkSublessorAccount");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/checkInviterAccount", "/management/v1/checkInviterAccount");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/registerAgent", "/management/v1/registerAgent");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/registerSublessor", "/management/v1/registerSublessor");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/sublessorLogin", "/management/v1/sublessorLogin");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/adminLogin", "/management/v1/adminLogin");
//		UserVerificationInterceptor.dispathcUrls.put("/management/v1/agentLogin", "/management/v1/agentLogin");
//		UserVerificationInterceptor.dispathcUrls.put("/swagger-resources", "/swagger-resources");
//		UserVerificationInterceptor.dispathcUrls.put("/v2/api-docs", "/v2/api-docs");
//		
//		UserVerificationInterceptor.dispathcUrls.put("/con/method", "/con/method");
//	}
}
