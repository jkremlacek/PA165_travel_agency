package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.CustomerCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.CustomerDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Kateřina Caletková
 */
public interface CustomerFacade {
    
    void create(CustomerCreateDto customer);
    
    CustomerDto findById(Long id);
    
    Set<CustomerDto> findAll();
    
    Set<CustomerDto> findByName(String name);
    
    Set<CustomerDto> findByBirthDate(Date date);
    
    Set<CustomerDto> findByPersonalNumber(Integer personalNumber);
    
    Set<CustomerDto> findByMail(String mail);
    
    Set<CustomerDto> findByPhoneNumber(Integer phoneNumber);
    
    Set<CustomerDto> findByReservation(ReservationDto reservation);
    
}
