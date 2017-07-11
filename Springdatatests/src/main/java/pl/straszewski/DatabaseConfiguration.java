package pl.straszewski;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by wojtek on 2017-06-04.
 */

@EnableJpaRepositories("pl.straszewski")
@ComponentScan("pl.straszewski")
@PropertySource("classpath:/application.properties")
@Configuration
public class DatabaseConfiguration {

    @Autowired
    Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
                factoryBean.setDataSource(dataSource());
                factoryBean.setJpaProperties(getJpaProperties());
                factoryBean.setPackagesToScan("pl.straszewski");
                factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            return factoryBean;
    }

    @Bean
    public Properties getJpaProperties() {
        Properties properties = new Properties();
      properties.setProperty("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
       properties.setProperty("hibernate.dialect",env.getProperty("hibernate.dialect"));
       properties.setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));

        return properties;
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

       dataSource.setDriverClassName(env.getProperty("db.driver"));
       dataSource.setUrl(env.getProperty("db.url"));
       dataSource.setUsername(env.getProperty("db.username"));
       dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;

        }

    @Bean
    public PlatformTransactionManager transactionManager (EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.afterPropertiesSet();

        return jpaTransactionManager;

    }

    }


