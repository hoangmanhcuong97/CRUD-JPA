package com.listcrush.config;


import com.listcrush.repository.crush.CrushRepo;
import com.listcrush.repository.crush.ICrushRepo;
import com.listcrush.service.CrushService;
import com.listcrush.service.ICrushService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.listcrush.controller")
@PropertySource("classpath:FilePictureCrush.properties")
public class AppConfig implements WebMvcConfigurer,ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5");
        this.applicationContext = applicationContext;
    }

    @Value("${file-crush}")
    private String fileCrush;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        System.out.println("5");
        SpringResourceTemplateResolver tem = new SpringResourceTemplateResolver();
        tem.setApplicationContext(applicationContext);
        tem.setPrefix("/WEB-INF/views");
        tem.setSuffix(".html");
        tem.setTemplateMode(TemplateMode.HTML);
        tem.setCharacterEncoding("UTF-8");
        return tem;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        System.out.println("6");
        SpringTemplateEngine temp = new SpringTemplateEngine();
        temp.setTemplateResolver(templateResolver());
        return temp;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        System.out.println("7");
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("UTF-8");
        return resolver;
    }

    // Cấu hình ORM JPA
    public Properties additionalProperties() {
        System.out.println("8");
        Properties pr = new Properties();
        pr.setProperty("hibernate.hbm2ddl.auto","update");
        pr.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        pr.setProperty("show_sql", "true");
        return pr;
    }

    @Bean
    public DataSource dataSource(){
        System.out.println("9");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/managercrush");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory){
        System.out.println("10");
        return entityManagerFactory.createEntityManager();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        System.out.println("11");
        LocalContainerEntityManagerFactoryBean eMFB = new LocalContainerEntityManagerFactoryBean();
        eMFB.setDataSource(dataSource());
        eMFB.setPackagesToScan("com.listcrush.model");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        eMFB.setJpaVendorAdapter(jpaVendorAdapter);
        eMFB.setJpaProperties(additionalProperties());
        return eMFB;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory eMF){
        System.out.println("12");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(eMF);
        return transactionManager;
    }
//
////    Cấu hình Upload File
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        System.out.println("13");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + fileCrush);
    }
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        System.out.println("14");
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800);
        return resolver;
    }

//  Khai báo Bean cho các Interface cần gọi
    @Bean
    public ICrushRepo crushRepo() {
        return new CrushRepo();
    }
    @Bean
    public ICrushService crushService(){
        return new CrushService();
    }
}
