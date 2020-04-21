package com.project.fitnessfinder.exception;

import static java.lang.String.format;

public class EntityNotFound extends RuntimeException {

    public EntityNotFound(String entity, Long id) {
        super(format("%s %s not found", entity, id));
    }
}
