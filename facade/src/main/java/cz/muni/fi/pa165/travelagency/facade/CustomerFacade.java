package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.CustomerCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.CustomerDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kateřina Caletková
 */
public interface CustomerFacade {
    
    void create(CustomerCreateDto customer);
    
    CustomerDto findById(Long id);
    
    CustomerDto findAll();
    
    List<CustomerDto> findByName(String name);
    
    List<CustomerDto> findByBirthDate(Date date);
    
    List<CustomerDto> findByPersonalNumber(Integer personalNumber);
    
    List<CustomerDto> findByMail(String mail);
    
    List<CustomerDto> findByPhoneNumber(Integer phoneNumber);
    
    List<CustomerDto> findByReservation(ReservationDto reservation);
    
}
