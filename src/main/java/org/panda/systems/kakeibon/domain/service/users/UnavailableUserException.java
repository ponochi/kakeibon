package org.panda.systems.kakeibon.domain.service.users;

public class UnavailableUserException extends RuntimeException {
    public UnavailableUserException(String message) {
        super(message);
    }
}
