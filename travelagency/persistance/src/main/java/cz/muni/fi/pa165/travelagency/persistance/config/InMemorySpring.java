package cz.muni.fi.pa165.travelagency.persistance.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

/**
 * Created on 21.10.2016.
 *
 * @author Martin Salata
 */
@Configuration
public class InMemorySpring {

    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory(){
        return new LocalEntityManagerFactoryBean();
    }


    @Bean
    public DataSource db() {
        EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
        return edb.setType(H2).build();

    }
}
