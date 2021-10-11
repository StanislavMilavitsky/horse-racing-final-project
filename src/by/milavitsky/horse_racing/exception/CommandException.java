package by.milavitsky.horse_racing.exception;

public class CommandException extends Exception {
    /**
     * Instantiates a new Command by.milavitsky.horse_racing.exception.
     */
    public CommandException() {
        super();
    }

    /**
     * Instantiates a new Command by.milavitsky.horse_racing.exception.
     *
     * @param message the message
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Command by.milavitsky.horse_racing.exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Command by.milavitsky.horse_racing.exception.
     *
     * @param cause the cause
     */
    public CommandException(Throwable cause) {
        super(cause);
    }
}
