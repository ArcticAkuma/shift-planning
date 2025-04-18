package haw.hof.shiftplanning.user.contract;

import haw.hof.shiftplanning.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    List<Contract> findContractsByUser(User user, Pageable pageable);
    Optional<Contract> findFirstByUserAndValidToGreaterThanEqualOrderByCreatedAtDesc(User user, LocalDate validToIsGreaterThan);
}
