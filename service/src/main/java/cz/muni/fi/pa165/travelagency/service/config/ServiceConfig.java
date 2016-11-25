package cz.muni.fi.pa165.travelagency.service.config;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created on 14.11.2016.
 *
 * @author Martin Salata
 */
@Configuration
@Import(InMemorySpring.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165.travelagency.service",
    "cz.muni.fi.pa165.travelagency.facade"})
public class ServiceConfig {

    @Bean
    public Mapper dozer(){
        DozerBeanMapper mapper = new DozerBeanMapper();
        return mapper;
    }
}
