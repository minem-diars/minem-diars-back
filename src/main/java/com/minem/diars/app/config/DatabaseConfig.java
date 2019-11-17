package com.minem.diars.app.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.minem.diars.app.util.constants.MinemConstants;

@Configuration
@EnableTransactionManagement
//@ConfigurationProperties(prefix = "spring")
public class DatabaseConfig {
	
	@Autowired
	private Environment env;
	
//	@Value("${spring.datasource.driver-class-name}")
//	private String driverClassName;
//	
//	@Value("${spring.datasource.url}")
//    private String url;
//	
//	@Value("${spring.datasource.username}")
//    private String username;
//	
//	@Value("${spring.datasource.password}")
//    private String password;
//	
//	@Value("${spring.jpa.database-platform}")
//    private String databasePlatform;
//	
//	@Value("${spring.jpa.show-sql}")
//    private String showSql;
//	
//	@Value("${spring.jpa.hibernate.ddl-auto}")
//    private String ddlAuto;
//    
    private String packages = MinemConstants.BASE_PACKAGE;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan(packages);
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		Properties additionalProperties = new Properties();
		additionalProperties.put(MinemConstants.HIBERNATE_DIALECT, env.getProperty("spring.jpa.database-platform"));
		additionalProperties.put(MinemConstants.HIBERNATE_SHOW_SQL, env.getProperty("spring.jpa.show-sql"));
		additionalProperties.put(MinemConstants.HIBERNATE_HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.ddl-auto"));
		entityManagerFactory.setJpaProperties(additionalProperties);
		return entityManagerFactory;
	}

}
