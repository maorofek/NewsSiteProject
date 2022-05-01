package iob.util;

import java.util.UUID;

/**
 * A class holding general utility methods.
 */
public final class MiscUtils {

    private static MiscUtils instance;

    private MiscUtils() {}

    public static MiscUtils instance() {
        if (instance == null) instance = new MiscUtils();
        return instance;
    }

    /**
     * Get the root cause of a throwable by traversing its cause stack.
     *
     * @param throwable the throwable we investigate.
     * @return the root throwable cause for the input throwable.
     */
    public Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        Throwable result = throwable;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    /**
     * Generate a new entity ID using UUID generation
     *
     * @return Universal unique identifier
     */
    public String getNewID() {
        return UUID.randomUUID().toString();
    }

}
