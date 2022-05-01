package iob.logic;

import iob.util.NamedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends NamedException {

    private static final long serialVersionUID = -8690185777610602390L;

    public UnauthorizedException(String _msg, Object... args) {
        super(_msg, args);
    }

    public UnauthorizedException(String _msg) {
        super(_msg);
    }

    public UnauthorizedException(Throwable throwable) {
        super(throwable);
    }
}
