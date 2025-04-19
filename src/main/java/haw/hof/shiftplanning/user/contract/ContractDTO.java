package haw.hof.shiftplanning.user.contract;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ContractDTO(
        int id,
        int userId,
        int workingHours,
        @NotNull LocalDate validFrom,
        @NotNull LocalDate validTo
) {
}
