package haw.hof.shiftplanning.user.util;

import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;

public enum UserLanguage {
    ENGLISH;

    public static UserLanguage getUserLanguage(String language) {
        try {
            return valueOf(language.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new EntityNotFoundException("language", language);
        }
    }
}
