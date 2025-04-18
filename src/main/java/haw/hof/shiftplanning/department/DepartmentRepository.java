package haw.hof.shiftplanning.department;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findByNameStartingWith(String prefix, Pageable pageable);
}
