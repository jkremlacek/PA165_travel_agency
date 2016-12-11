package cz.muni.fi.pa165.travelagency.web.converter;

import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class IdToTripConverter implements Converter<String, TripDto> {

    @Inject
    private TripFacade tripFacade;

    @Override
    public TripDto convert(String id) {
        return tripFacade.findById(Long.parseLong(id));
    }
}