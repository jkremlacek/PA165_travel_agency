package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.*;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    @Inject
    MappingService mappingService;

    @Inject
    ReservationService reservationService;

    @Override
    public void create(ReservationCreateDto reservation) {
        reservationService.create(mappingService.mapTo(reservation, Reservation.class));
    }

    @Override
    public void update(ReservationDto reservationDto) {
        reservationService.update(mappingService.mapTo(reservationDto, Reservation.class));
    }

    @Override
    public void delete(ReservationDto reservationDto) {
        reservationService.delete(mappingService.mapTo(reservationDto, Reservation.class));
    }

    @Override
    public ReservationDto findById(Long id) {
        return mappingService.mapTo(reservationService.findById(id), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findAll() {
        return mappingService.mapTo(reservationService.findAll(), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findByUser(UserDto user) {
        User mapped = mappingService.mapTo(user, User.class);
        return mappingService.mapTo(reservationService.findByUser(mapped), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findByTrip(TripDto trip) {
        Trip mapped = mappingService.mapTo(trip, Trip.class);
        return mappingService.mapTo(reservationService.findByTrip(mapped), ReservationDto.class);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return reservationService.getTotalPrice(id);
    }

    @Override
    public ReservationDto addExcursion(Long reservationId, ExcursionDto excursionDto) {
        Excursion excursion = mappingService.mapTo(excursionDto,Excursion.class);
        return mappingService.mapTo(reservationService.addExcursion(reservationId,excursion), ReservationDto.class);
    }
}
