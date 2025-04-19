package haw.hof.shiftplanning.department;

import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;
import haw.hof.shiftplanning.shift.type.ShiftTypeDTO;
import haw.hof.shiftplanning.shift.type.ShiftTypeMapper;
import haw.hof.shiftplanning.shift.type.ShiftTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ShiftTypeRepository shiftTypeRepository;

    private final DepartmentMapper departmentMapper;
    private final ShiftTypeMapper shiftTypeMapper;

    public List<Department> getDepartments(int offset, int limit) {
        return this.departmentRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public List<DepartmentDTO> getDepartmentsDTO(int offset, int limit) {
        return this.departmentMapper.toDTO(this.getDepartments(offset, limit));
    }

    public Department getDepartmentById(Integer id) {
        return this.departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("department", id));
    }

    public DepartmentDTO getDepartmentDTOById(Integer id) {
        return this.departmentMapper.toDTO(this.getDepartmentById(id));
    }

    public List<DepartmentDTO> searchDepartments(String searchTerm, int offset, int limit) {
        return this.departmentMapper.toDTO(this.departmentRepository.findDepartmentByNameStartingWith(searchTerm, PageRequest.of(offset, limit)));
    }

    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        return this.departmentMapper.toDTO(this.departmentMapper.toEntity(departmentDTO));
    }

    @Transactional
    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO departmentDTO) {
        Department department = this.getDepartmentById(id);
        department.setName(departmentDTO.name());
        return this.departmentMapper.toDTO(this.departmentRepository.save(department));
    }

    public DepartmentDTO deleteDepartment(Integer id) {
        //Department department = getDepartmentById(id);
        //todo: only delete if NO shift plans archived yet: DataIntegrityViolationException - or create new one: ArchiveIntegrityViolationException
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ShiftTypeDTO> getShiftTypes(Department department, boolean active, int offset, int limit) {
        return this.shiftTypeMapper.toDTO(this.shiftTypeRepository.findShiftTypeByDepartmentAndActive(department, active, PageRequest.of(offset, limit)));
    }
}
