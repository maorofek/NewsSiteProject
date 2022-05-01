package iob.logic;

import iob.util.NamedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInputException extends NamedException {

    private static final long serialVersionUID = -8497386252247654547L;

    public InvalidInputException(String _msg, Object... args) {
        super(_msg, args);
    }

    public InvalidInputException(String _msg) {
        super(_msg);
    }

    public InvalidInputException(Throwable throwable) {
        super(throwable);
    }
}
