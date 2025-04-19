package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.department.DepartmentService;
import haw.hof.shiftplanning.user.util.UserLanguage;
import haw.hof.shiftplanning.user.util.UserRole;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleToRoleName")
    @Mapping(source = "language", target = "language", qualifiedByName = "mapLanguageToLanguageName")
    UserDTO toDTO(User user);

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "mapDepartmentIdToDepartment")
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleNameToRole")
    @Mapping(source = "language", target = "language", qualifiedByName = "mapLanguageNameToLanguage")
    User toEntity(UserDTO userDTO, @Context DepartmentService departmentService);

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleToRoleName")
    @Mapping(source = "language", target = "language", qualifiedByName = "mapLanguageToLanguageName")
    List<UserDTO> toDTO(List<User> users);

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "mapDepartmentIdToDepartment")
    @Mapping(source = "role", target = "role", qualifiedByName = "mapRoleNameToRole")
    @Mapping(source = "language", target = "language", qualifiedByName = "mapLanguageNameToLanguage")
    List<User> toEntity(List<UserDTO> userDTOS);

    @Named("mapDepartmentIdToDepartment")
    default Department mapDepartmentIdToDepartment(int departmentId, @Context DepartmentService departmentService) {
        return departmentService.getDepartmentById(departmentId);
    }

    @Named("mapRoleToRoleName")
    default String mapRoleToRoleName(UserRole userRole) {
        return userRole.name();
    }

    @Named("mapRoleNameToRole")
    default UserRole mapRoleNameToRole(String roleName) {
        return UserRole.getUserRole(roleName);
    }

    @Named("mapLanguageToLanguageName")
    default String mapLanguageToLanguageName(UserLanguage userLanguage) {
        return userLanguage.name();
    }

    @Named("mapLanguageNameToLanguage")
    default UserLanguage mapLanguageNameToLanguage(String languageName) {
        return UserLanguage.getUserLanguage(languageName);
    }
}
