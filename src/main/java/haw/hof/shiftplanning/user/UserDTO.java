package haw.hof.shiftplanning.user;

import jakarta.validation.constraints.NotNull;

public record UserDTO(
        int id,
        @NotNull String firstName,
        @NotNull String lastName,
        int departmentId,
        @NotNull String role,
        @NotNull String email,
        @NotNull String language,
        int personnelId,
        boolean active
) {
}
