package haw.hof.shiftplanning.department;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findDepartmentByName(String name);  //todo: ignore case?
    List<Department> findDepartmentByNameStartingWith(String prefix, Pageable pageable);
}
