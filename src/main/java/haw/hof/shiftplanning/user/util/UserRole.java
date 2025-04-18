package haw.hof.shiftplanning.user.util;

import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;

public enum UserRole {
    ADMINISTRATOR,
    HUMAN_RESOURCES,
    MANAGER,
    EMPLOYEE;

    public static UserRole getUserRole(String role) {
        try {
            return valueOf(role.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new EntityNotFoundException("user role", role);
        }
    }
}
