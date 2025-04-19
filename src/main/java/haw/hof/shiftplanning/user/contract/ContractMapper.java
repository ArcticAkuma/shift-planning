package haw.hof.shiftplanning.user.contract;

import haw.hof.shiftplanning.user.User;
import haw.hof.shiftplanning.user.UserService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(source = "user.id", target = "userId")
    ContractDTO toDTO(Contract contract);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUserIdToUser")
    Contract toEntity(ContractDTO contractDTO, @Context UserService userService);

    @Mapping(source = "user.id", target = "userId")
    List<ContractDTO> toDTO(List<Contract> contracts);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUserIdToUser")
    List<Contract> toEntity(List<ContractDTO> contractDTOS);

    @Named("mapUserIdToUser")
    default User mapDepartmentIdToDepartment(int userId, @Context UserService userService) {
        return userService.getUserById(userId);
    }
}
