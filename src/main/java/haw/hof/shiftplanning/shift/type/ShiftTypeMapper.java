
package haw.hof.shiftplanning.shift.type;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.department.DepartmentService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShiftTypeMapper {

    @Mapping(source = "department.id", target = "departmentId")
    ShiftTypeDTO toDTO(ShiftType shiftType);

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "mapDepartmentIdToDepartment")
    ShiftType toEntity(ShiftTypeDTO shiftTypeDTO, @Context DepartmentService departmentService);

    @Mapping(source = "department.id", target = "departmentId")
    List<ShiftTypeDTO> toDTO(List<ShiftType> shiftTypes);

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "mapDepartmentIdToDepartment")
    List<ShiftType> toEntity(List<ShiftTypeDTO> shiftTypeDTOS);

    @Named("mapDepartmentIdToDepartment")
    default Department mapDepartmentIdToDepartment(int departmentId, @Context DepartmentService departmentService) {
        return departmentService.getDepartmentById(departmentId);
    }
}
