package cz.muni.fi.pa165.travelagency.persistance.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Customer {
    
    private Long id;
    private String name;
    private Date birthDate;
    private Integer personalNumber;
    private String mail;
    private Integer phoneNumber;
    private Set<Reservation> reservations = new HashSet<Reservation>();
    
}
