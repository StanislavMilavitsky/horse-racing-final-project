package by.milavitsky.horseracing.exception;
/**
 * The type Service exception which occurs when working with service layer.
 */
public class ServiceException extends Exception {
    /**
     * Instantiates a new Service by.milavitsky.horse_racing.exception.
     */
    public ServiceException() {
        super();
    }

    /**
     * Instantiates a new Service by.milavitsky.horse_racing.exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Service by.milavitsky.horse_racing.exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Service by.milavitsky.horse_racing.exception.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
