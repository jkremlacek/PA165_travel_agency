package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.CustomerDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;

import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public class ReservationFacadeImpl implements ReservationFacade{
    public void create(ReservationCreateDto reservation) {
    }

    public ReservationDto findById(Long id) {
        return null;
    }

    public List<ReservationDto> findAll() {
        return null;
    }

    public List<ReservationDto> findByCustomer(CustomerDto customer) {
        return null;
    }

    public List<ReservationDto> findByTrip(TripDto trip) {
        return null;
    }
}
