package haw.hof.shiftplanning.shift.type;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ShiftTypeDTO(
        int id,
        int departmentId,
        @NotNull String name,
        @NotNull String description,
        @NotNull String colorCode,
        @NotNull LocalTime startTime,
        int duration,
        boolean active
) {
}
