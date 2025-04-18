package haw.hof.shiftplanning.department;

import haw.hof.shiftplanning.exception.exception.EntityAlreadyExistsException;
import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments(int offset, int limit) {
        return this.departmentRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public Department getDepartmentById(Integer id) {
        return this.departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("department", id));
    }

    public List<Department> searchDepartments(String searchTerm, int offset, int limit) {
        return this.departmentRepository.findDepartmentByNameStartingWith(searchTerm, PageRequest.of(offset, limit));
    }

    public Department addDepartment(Department department) {
        try {
            return this.departmentRepository.save(new Department(department.getName()));
        } catch (DataIntegrityViolationException exception) {
            //todo: throw exception or return existing one?
            throw new EntityAlreadyExistsException("department", department.getName());
        }
    }

    public Department updateDepartment(Integer id, Department updatedDepartment) {
        try {
            Department department = getDepartmentById(id);
            department.setName(updatedDepartment.getName());
            return this.departmentRepository.save(department);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityAlreadyExistsException("department", updatedDepartment.getName());
        }
    }

    public Department deleteDepartment(@PathVariable Integer id) {
        //Department department = getDepartmentById(id);
        //todo: only delete if NO shift plans archived yet: DataIntegrityViolationException - or create new one: ArchiveIntegrityViolationException
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
