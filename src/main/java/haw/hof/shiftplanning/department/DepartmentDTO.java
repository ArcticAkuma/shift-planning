package haw.hof.shiftplanning.department;

import jakarta.validation.constraints.NotNull;

public record DepartmentDTO(
        int id,
        @NotNull String name) {
}
