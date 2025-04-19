
package haw.hof.shiftplanning.department;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDTO toDTO(Department department);

    Department toEntity(DepartmentDTO departmentDTO);

    List<DepartmentDTO> toDTO(List<Department> departments);

    List<Department> toEntity(List<DepartmentDTO> departmentDTOs);
}
