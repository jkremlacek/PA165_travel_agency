package cz.muni.fi.pa165.travelagency.service.exception;

import org.springframework.dao.DataAccessException;

/**
 * Created on 17.11.2016.
 *
 * @author Martin Salata
 */
public class PersistenceException extends DataAccessException {

    public PersistenceException(String msg) {
        super(msg);
    }

    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
