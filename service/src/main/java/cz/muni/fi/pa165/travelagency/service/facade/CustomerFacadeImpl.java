package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.service.CustomerService;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kateřina Caletková
 */

@Service
@Transactional
public class CustomerFacadeImpl /*implements CustomerFacade*/ {
    
    @Inject
    private CustomerService customerService;
    
}
