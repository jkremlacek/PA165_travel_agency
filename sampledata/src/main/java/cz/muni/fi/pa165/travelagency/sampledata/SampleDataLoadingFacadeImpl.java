package cz.muni.fi.pa165.travelagency.sampledata;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads some sample data to populate the travel agency database.
 * 
 * @author Jakub Kremláček
 */

@Component
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);
    
    @Autowired
    private ExcursionService excursionService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private TripService tripService;
    
    @Autowired
    private UserService userService;
    
    @Override
    @SuppressWarnings("unused")
    public void loadData() {
        
        User admin = user("admin@pa165.com", "password", "Admin Strong", date(1988, Calendar.JANUARY, 1), "8801014444", "123456789", true);
        User customerSheep = user("sheep@pa165.com", "password", "Ben Sheep", date(1971, Calendar.JUNE, 20), "7106203333", "987654321", false);
        User customerJane = user("jane@pa165.com", "password", "Jane Cute", date(1990, Calendar.NOVEMBER, 16), "9066163333", "732015789", false);
        User customerPablo = user("pablo@pa165.com", "password", "Pablo Smart", date(1995, Calendar.MAY, 25), "9505253456", "605015789", false);
        
        log.info("Loaded users.");
        
        Trip austriaTrip = trip("Beautiful Austria", addDaysAfterNowAndSetExactlyDayHourAndMinutes(21, 8, 0),
                                addDaysAfterNowAndSetExactlyDayHourAndMinutes(28, 17, 0), 
                                "Austria, Alps", "Enjoy marvellous hotel in the Alps.", 50, 
                                BigDecimal.valueOf(6399));
        Trip londonTrip = trip("Gorgeous London", addDaysAfterNowAndSetExactlyDayHourAndMinutes(14, 7, 0),
                                addDaysAfterNowAndSetExactlyDayHourAndMinutes(18, 21, 30), "UK, London", 
                                "Visit the capital city of the UK.", 200, BigDecimal.valueOf(14099));
        Trip parisTrip = trip("Day in Paris", addDaysAfterNowAndSetExactlyDayHourAndMinutes(59, 6, 0), 
                              addDaysAfterNowAndSetExactlyDayHourAndMinutes(61, 18, 30), "France, Paris", 
                              "Visit the most romantic city of Europe for one day.", 132, BigDecimal.valueOf(2399));
        Trip usaTrip = trip("Endurance through USA", addDaysAfterNowAndSetExactlyDayHourAndMinutes(7, 22, 0), 
                            addDaysAfterNowAndSetExactlyDayHourAndMinutes(38, 6, 45), "USA", 
                            "Tour through many states of the USA.", 181, BigDecimal.valueOf(149999));
        
        log.info("Loaded trips.");

        Excursion dachsteinExcursion = excursion(
                "Dachstein tour", addDaysAfterNowAndSetExactlyDayHourAndMinutes(22, 10, 0),
                7, "Austria, Dachstein", BigDecimal.valueOf(900), austriaTrip);
        Excursion viennaExcursion = excursion(
                "Vienna", addDaysAfterNowAndSetExactlyDayHourAndMinutes(25, 9, 0),
                3, "Austria, Vienna", BigDecimal.valueOf(1200), austriaTrip);
        Excursion bigbenExcursion = excursion(
                "Big Ben", addDaysAfterNowAndSetExactlyDayHourAndMinutes(15, 7, 30),
                9, "UK, London, Big Ben", BigDecimal.valueOf(800), londonTrip);
        Excursion londonEye = excursion(
                "London eye", addDaysAfterNowAndSetExactlyDayHourAndMinutes(17, 14, 0),
                5, "UK, London", BigDecimal.valueOf(1000), londonTrip);
        Excursion towerBridge = excursion(
                "Tower Bridge", addDaysAfterNowAndSetExactlyDayHourAndMinutes(16, 9, 30),
                2, "UK, London", BigDecimal.valueOf(100), londonTrip);
        Excursion eiffelExcursion = excursion(
                "Eiffel tower", addDaysAfterNowAndSetExactlyDayHourAndMinutes(60, 9, 0), 
                1, "France, Paris, Eiffel tower", BigDecimal.valueOf(400), parisTrip);
        Excursion notreDameExcursion = excursion(
                "Notre Dame", addDaysAfterNowAndSetExactlyDayHourAndMinutes(60, 13, 0), 
                1, "France, Paris, Notre Dame", BigDecimal.valueOf(300), parisTrip);
        Excursion franceMuseumExcursion = excursion(
                "National Museum of France", addDaysAfterNowAndSetExactlyDayHourAndMinutes(60, 15, 30), 
                2, "France, Paris, National Museum of France", BigDecimal.valueOf(300), parisTrip);
        
        log.info("Loaded excursions.");
        
        HashSet<Excursion> customer1ExcursionSet = new HashSet<>();
        customer1ExcursionSet.add(eiffelExcursion);
        customer1ExcursionSet.add(notreDameExcursion);
        
        HashSet<Excursion> customer2ExcursionSet = new HashSet<>();
        customer2ExcursionSet.add(bigbenExcursion);
        customer2ExcursionSet.add(londonEye);
        customer2ExcursionSet.add(towerBridge);
        
        log.info("Prepared excursion sets.");
        
        Reservation sheepUsa = reservation(customerSheep, new HashSet<>(), usaTrip);
        Reservation janeParis = reservation(customerJane, customer1ExcursionSet, parisTrip);
        Reservation pabloTrip = reservation(customerPablo, customer2ExcursionSet, londonTrip);
        
        log.info("Loaded reservations.");
            
    }
    
    private Excursion excursion(String name, Date date, Integer duration, 
            String destination, BigDecimal price, Trip trip) {
        Excursion e = new Excursion(name, date, duration, destination, price);
        
        e.setTrip(trip);
        trip.addExcursion(e);
        
        excursionService.create(e);
        tripService.update(trip);
        
        return e;
    }
    
    private Reservation reservation(User user, Set<Excursion> excursionSet, Trip trip) {
        Reservation r = new Reservation(user, excursionSet, trip);
        
        trip.addReservation(r);
        user.addReservation(r);
        
        reservationService.create(r);
        tripService.update(trip);
        userService.update(user);
        
        return r;
    }
    
    private Trip trip(String name, Date dateFrom, Date dateTo, String destination,
                      String description, Integer capacity, BigDecimal price) {
        Trip t = new Trip(name, dateFrom, dateTo, destination, description, capacity, price);
        
        tripService.create(t);
        
        return t;
    }
    
    private User user(String mail, String password, String name, Date birthDate,
                      String personalNumber, String phoneNumber, Boolean isAdmin) {
        User u = new User(name, birthDate, personalNumber, mail, phoneNumber);
        u.setIsAdmin(isAdmin);
        userService.createRegisteredUser(u, password);
        
        return u;
    }
    
    private static Date addDaysAfterNowAndSetExactlyDayHourAndMinutes(
                        int days, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        
        cal.add(Calendar.DAY_OF_MONTH, days);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        
        return cal.getTime();
    }

    private static Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        
        return cal.getTime();
    }
    
}
