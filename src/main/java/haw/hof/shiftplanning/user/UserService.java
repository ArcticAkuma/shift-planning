package haw.hof.shiftplanning.user;

import haw.hof.shiftplanning.department.Department;
import haw.hof.shiftplanning.department.DepartmentRepository;
import haw.hof.shiftplanning.department.DepartmentService;
import haw.hof.shiftplanning.exception.exception.EntityNotFoundException;
import haw.hof.shiftplanning.user.contract.Contract;
import haw.hof.shiftplanning.user.contract.ContractDTO;
import haw.hof.shiftplanning.user.contract.ContractMapper;
import haw.hof.shiftplanning.user.contract.ContractRepository;
import haw.hof.shiftplanning.user.util.UserLanguage;
import haw.hof.shiftplanning.user.util.UserRole;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor

@Service
public class UserService {
    //todo: add oauth2 (DefaultOAuth2UserService ?)

    private DepartmentService departmentService;

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ContractRepository contractRepository;

    private final UserMapper userMapper;
    private final ContractMapper contractMapper;

    public List<User> getUsers(int offset, int limit) {
        return this.userRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public List<UserDTO> getUsersDTO(int offset, int limit) {
        return this.userMapper.toDTO(this.getUsers(offset, limit));
    }

    public User getUserById(int id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));
    }

    public UserDTO getUserDTOById(int id) {
        return this.userMapper.toDTO(this.getUserById(id));
    }

    public List<UserDTO> searchUsers(String searchTerm, String departmentName, String roleName, int offset, int limit) {
        return this.userMapper.toDTO(this.userRepository.findAll((root, query, cb) -> {

            Department department = departmentName != null && !departmentName.isBlank() ?
                    this.departmentRepository.findDepartmentByName(departmentName).orElseThrow(() -> new EntityNotFoundException("department", departmentName)) : null;
            UserRole role = roleName != null && !roleName.isBlank() ? UserRole.getUserRole(roleName) : null;

            List<Predicate> predicates = new ArrayList<>();
            if (searchTerm != null && !searchTerm.isBlank())
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"));
            if (department != null) predicates.add(cb.equal(root.get("department"), department));
            if (role != null) predicates.add(cb.equal(root.get("role"), role));

            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(offset, limit)).getContent());

    }

    public List<User> getByDepartment(Department department, boolean active, int offset, int limit) {
        return this.userRepository.findUserByDepartmentAndActive(department, active, PageRequest.of(offset, limit));
    }

    public UserDTO addUser(UserDTO user) {
        return this.userMapper.toDTO(this.userRepository.save(this.userMapper.toEntity(user, this.departmentService)));
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO updatedUser) {
        User user = getUserById(id);
        user.setFirstName(updatedUser.firstName());
        user.setLastName(updatedUser.lastName());
        user.setDepartment(this.departmentService.getDepartmentById(updatedUser.departmentId()));
        user.setRole(UserRole.getUserRole(updatedUser.role()));
        user.setLanguage(UserLanguage.getUserLanguage(updatedUser.language()));
        user.setActive(updatedUser.active());
        //todo: password
        return this.userMapper.toDTO(this.userRepository.save(user));
    }

    public List<Contract> getUserContracts(User user, int offset, int limit) {
        return this.contractRepository.findContractsByUser(user, PageRequest.of(offset, limit));
    }

    public List<ContractDTO> getUserContractsDTO(User user, int offset, int limit) {
        return this.contractMapper.toDTO(this.getUserContracts(user, offset, limit));
    }

    public Contract getActiveContract(User user) {
        return this.contractRepository.findFirstByUserAndValidToGreaterThanEqualOrderByCreatedAtDesc(user, LocalDate.now())
                .orElseThrow(() -> new EntityNotFoundException("active contract"));
    }

    public ContractDTO getActiveContractDTO(User user) {
        return this.contractMapper.toDTO(getActiveContract(user));
    }

    @Transactional
    public ContractDTO setUserContract(User user, ContractDTO contract) {
        if (contract.validTo().isBefore(LocalDate.now()) || contract.validTo().isBefore(contract.validFrom()) ||
                user.getId() != contract.userId()) {  //todo: check if workingHours is in legal boundaries! -> config system [sprint2]
            throw new IllegalArgumentException("Contract is not valid");
        }
        return this.contractMapper.toDTO(this.contractRepository.save(this.contractMapper.toEntity(contract, this)));
    }
}
