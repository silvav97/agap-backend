package com.agap.management.exceptions.personalizedException;

public class EntityNotFoundByFieldException extends RuntimeException {

    public static String BASE = "%s con %s: %s no fue encontrado";

    public EntityNotFoundByFieldException(String entity, String field, String fieldValue) {
        super(String.format(BASE, entity, field, fieldValue));
    }

}
