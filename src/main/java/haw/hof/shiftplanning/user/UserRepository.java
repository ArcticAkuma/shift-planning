package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByDepartment(Department department, Pageable pageable);
}
