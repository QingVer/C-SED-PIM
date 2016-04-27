/**
 * An exception to be thrown the the user is quiting
 * the current menu.
 * @author George Andrews
 * @version 1.0, Sprint 1
 */
@SuppressWarnings("serial")
public class QuitException extends Exception {

	QuitException(String message) {
        super(message);
    }

    QuitException() {
        super();
    }
}
