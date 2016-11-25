package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.UserCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.service.UserService;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of user facade.
 * @author Kateřina Caletková
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {
    
    @Inject
    MappingService mappingService;
    
    @Inject
    private UserService userService;

    @Override
    public void create(UserCreateDto user) {    
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getPasswordHash() == null ) {
            throw new IllegalArgumentException("Password is null");
        }
        if (user.getPasswordHash().isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }
        User mapped = mappingService.mapTo(user, User.class);
	userService.createRegisteredUser(mapped, user.getPasswordHash());	        
    }

    @Override
    public UserDto findById(Long id) {        
        User user = userService.findById(id);
        if (user == null) {
            return null;
        }
        return mappingService.mapTo(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return mappingService.mapTo(userService.findAll(), UserDto.class);
    }

    @Override
    public List<UserDto> findByName(String name) {
        return mappingService.mapTo(userService.findByName(name), UserDto.class);
    }

    @Override
    public List<UserDto> findByBirthDate(Date date) {
        return mappingService.mapTo(userService.findByBirthDate(date), UserDto.class);
    }

    @Override
    public List<UserDto> findByPersonalNumber(Long personalNumber) {
        return mappingService.mapTo(userService.findByPersonalNumber(personalNumber), UserDto.class);
    }

    @Override
    public List<UserDto> findByMail(String mail) {
        return mappingService.mapTo(userService.findByMail(mail), UserDto.class);
    }

    @Override
    public List<UserDto> findByPhoneNumber(Integer phoneNumber) {
        return mappingService.mapTo(userService.findByPhoneNumber(phoneNumber), UserDto.class);
    }

    @Override
    public List<UserDto> findByReservation(ReservationDto reservation) {
        Reservation mapped = mappingService.mapTo(reservation, Reservation.class);
        return mappingService.mapTo(userService.findByReservation(mapped), UserDto.class);
    }

    @Override
    public void update(UserDto user) {
        userService.update(mappingService.mapTo(user, User.class));
    }

    @Override
    public void delete(UserDto user) {
        userService.delete(mappingService.mapTo(user, User.class));
    }

    @Override
    public Boolean isUserAdmin(UserDto user) {
        return userService.isUserAdmin(mappingService.mapTo(user, User.class));
    }

    @Override
    public boolean userAuthenticate(UserAuthenticateDto user) {
        User userEntity = userService.findById(user.getId());
        return userService.userAuthenticate(userEntity, user.getPassword());
    }
    
}
