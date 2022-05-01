package iob.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ConversionException extends NamedException {

    private static final long serialVersionUID = 6935778528390864644L;

    public ConversionException(String _msg, Object... args) {
        super(_msg, args);
    }

    public ConversionException(String _msg) {
        super(_msg);
    }

    public ConversionException(Throwable throwable) {
        super(throwable);
    }
}