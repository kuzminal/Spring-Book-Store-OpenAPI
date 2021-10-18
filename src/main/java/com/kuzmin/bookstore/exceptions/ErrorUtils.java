package com.kuzmin.bookstore.exceptions;

public class ErrorUtils {
    private ErrorUtils() {
    }

    /**
     * Creates and return an error object
     *
     * @param httpStatusCode
     * @return error
     */
    public static Error createError(
            final Integer httpStatusCode) {
        Error error = new Error();
        error.setStatus(httpStatusCode);
        return error;
    }
}
