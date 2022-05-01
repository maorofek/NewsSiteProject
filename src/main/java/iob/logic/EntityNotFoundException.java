package iob.logic;

import iob.util.NamedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends NamedException {

    private static final long serialVersionUID = 3852712146678432725L;

    public EntityNotFoundException(String _msg, Object... args) {
        super(_msg, args);
    }

    public EntityNotFoundException(String _msg) {
        super(_msg);
    }

    public EntityNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
