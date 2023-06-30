package org.example.training;

class UnableToFindParticipantsException extends RuntimeException {
    public UnableToFindParticipantsException(String message, Throwable cause) {
        super(message, cause);
    }
}
