package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.UserCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserAuthenticateDto;
import java.util.Date;
import java.util.Set;

/**
 * Facade interface for User
 * @author Kateřina Caletková
 */
public interface UserFacade {
    
    void create(UserCreateDto user);
    
    void update(UserDto user);
    
    void delete(UserDto user);
    
    UserDto findById(Long id);
    
    Set<UserDto> findAll();
    
    Set<UserDto> findByName(String name);
    
    Set<UserDto> findByBirthDate(Date date);
    
    Set<UserDto> findByPersonalNumber(Integer personalNumber);
    
    Set<UserDto> findByMail(String mail);
    
    Set<UserDto> findByPhoneNumber(Integer phoneNumber);
    
    Set<UserDto> findByReservation(ReservationDto reservation);
    
    boolean isUserAdmin(UserDto user);
    
    boolean userAuthenticate (UserAuthenticateDto user);
    
}
