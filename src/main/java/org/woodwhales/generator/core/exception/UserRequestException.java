package org.woodwhales.generator.core.exception;

/**
 * @author woodwhales
 * @create 2020-10-08 21:35
 */
public class UserRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserRequestException(String message) {
        super(message);
    }

}
