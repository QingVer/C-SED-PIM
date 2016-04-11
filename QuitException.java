/**
 * Created by Georgew on 06/03/2016.
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
