package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.UserCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Kateřina Caletková
 */
public interface UserFacade {
    
    void create(UserCreateDto user);
    
    UserDto findById(Long id);
    
    Set<UserDto> findAll();
    
    Set<UserDto> findByName(String name);
    
    Set<UserDto> findByBirthDate(Date date);
    
    Set<UserDto> findByPersonalNumber(Integer personalNumber);
    
    Set<UserDto> findByMail(String mail);
    
    Set<UserDto> findByPhoneNumber(Integer phoneNumber);
    
    Set<UserDto> findByReservation(ReservationDto reservation);
    
}
