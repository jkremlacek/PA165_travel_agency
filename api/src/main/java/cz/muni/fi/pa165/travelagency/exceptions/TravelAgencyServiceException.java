package cz.muni.fi.pa165.travelagency.exceptions;

/**
 * Exception which represented failure on service layer
 * @author Katerina Caletkova
 */
public class TravelAgencyServiceException extends RuntimeException {
    
    public TravelAgencyServiceException() {
    }

    public TravelAgencyServiceException(String message) {
        super(message);
    }

    public TravelAgencyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TravelAgencyServiceException(Throwable cause) {
        super(cause);
    }
}
