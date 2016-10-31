package cz.muni.fi.pa165.travelagency.persistence.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * Created on 21.10.2016.
 *
 * @author Martin Salata
 */
@Configuration
@ComponentScan(basePackages = "cz.muni.fi.pa165.travelagency.persistence.dao")
@EnableTransactionManagement
@EnableJpaRepositories
public class InMemorySpring {

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(this.db());
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        return entityManagerFactoryBean;
    }


    @Bean
    public DataSource db() {
        EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
        return edb.setType(H2).build();

    }
}
