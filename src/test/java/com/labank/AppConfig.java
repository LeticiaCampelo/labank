package com.labank;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryLabank", transactionManagerRef = "transactionManagerLabank", basePackages = "com.repository")
public class AppConfig {
	@Bean(name = "dataSourceLabank")
	@Qualifier("labank")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource(){
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		return embeddedDatabaseBuilder
				.addScript("classpath:h2_init_script.sql")
				.setType(EmbeddedDatabaseType.H2)
				.build();
	}
	@Bean(name = "entityManagerFactoryLabank")
	@Qualifier("labank")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSourceLabank") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.model").build();
	}

	@Bean(name = "transactionManagerLabank")
	@Qualifier("labank")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactoryLabank") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
