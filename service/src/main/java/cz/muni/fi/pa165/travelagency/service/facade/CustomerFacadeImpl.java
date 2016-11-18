package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.CustomerFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.CustomerCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.CustomerDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.service.CustomerService;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import java.util.Date;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kateřina Caletková
 */

@Service
@Transactional
public class CustomerFacadeImpl implements CustomerFacade {
    
    @Inject
    MappingService mappingService;
    
    @Inject
    private CustomerService customerService;

    @Override
    public void create(CustomerCreateDto customer) {
        Customer mapped = mappingService.mapTo(customer, Customer.class);
		customerService.create(mapped);
        
    }

    @Override
    public CustomerDto findById(Long id) {
        return mappingService.mapTo(customerService.findById(id), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findAll() {
        return mappingService.mapTo(customerService.findAll(), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findByName(String name) {
        return mappingService.mapTo(customerService.findByName(name), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findByBirthDate(Date date) {
        return mappingService.mapTo(customerService.findByBirthDate(date), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findByPersonalNumber(Integer personalNumber) {
        return mappingService.mapTo(customerService.findByPersonalNumber(personalNumber), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findByMail(String mail) {
        return mappingService.mapTo(customerService.findByMail(mail), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findByPhoneNumber(Integer phoneNumber) {
        return mappingService.mapTo(customerService.findByPhoneNumber(phoneNumber), CustomerDto.class);
    }

    @Override
    public Set<CustomerDto> findByReservation(ReservationDto reservation) {
        Reservation mapped = mappingService.mapTo(reservation, Reservation.class);
        return mappingService.mapTo(customerService.findByReservation(mapped), CustomerDto.class);
    }
    
}
