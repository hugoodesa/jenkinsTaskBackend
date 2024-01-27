package br.com.stapassoli.taskBackendJenkins.exceptions;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(String message) {
        super(message);
    }
}
