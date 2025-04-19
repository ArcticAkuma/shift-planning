package haw.hof.shiftplanning.shift.type;

import haw.hof.shiftplanning.department.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftTypeRepository extends JpaRepository<ShiftType, Integer> {

    List<ShiftType> findShiftTypeByDepartmentAndActive(Department department, boolean active, Pageable pageable);
}
